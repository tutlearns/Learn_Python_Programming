package com.meet.learnpython;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryProductDetailsResult;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";
    // Replace with the Product ID you set in Google Play Console for "remove ads"
    private static final String REMOVE_ADS_PRODUCT_ID = "python_remove_ads";

    private BillingClient billingClient;
    private MyAdManager adManager;

    // Executor for background tasks
    private ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupEdgeToEdge();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adManager = new MyAdManager(this);
        executorService = Executors.newSingleThreadExecutor();

        // --- Google Play Billing Initialization ---
        billingClient = BillingClient.newBuilder(this)
                .setListener(this) // Set this activity as the listener for purchase updates
                .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()) // Required for handling pending purchases
                .build();

        // Connect to Google Play Billing service
        connectToBillingService();

        //AdSettings.setTestMode(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_example,R.id.rate,R.id.feedback,R.id.share,R.id.exit,R.id.more,R.id.ad_remove)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                int menuId = destination.getId();
                Menu menu = navigationView.getMenu();
                if (adManager.hasPurchasedRemoveAds()) {
                    menu.findItem(R.id.ad_remove).setVisible(false);
                }
                switch (menuId){
                    case R.id.rate:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.meet.learnpython"));
                        startActivity(intent);
                        break;
                    case R.id.more:
                        Intent moreintent = new Intent(Intent.ACTION_VIEW);
                        moreintent.setData(Uri.parse("market://search?q=pub:tutlearns"));
                        startActivity(moreintent);
                        break;
                    case R.id.share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,"Hey check out this application for the learning Python Programming: https://play.google.com/store/apps/details?id=com.meet.learnpython");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;
                    case R.id.feedback:
                        Intent fintent = new Intent(Intent.ACTION_SEND);
                        fintent.setType("plain/text");
                        fintent.putExtra(Intent.EXTRA_EMAIL, new String[] { "mailtomeet.it@gmail.com" });
                        fintent.putExtra(Intent.EXTRA_SUBJECT, "Feedback about Learn Python Programming application");
                        startActivity(Intent.createChooser(fintent, "Contact Us!"));
                        break;
                    case R.id.ad_remove:
                        initiateRemoveAdsPurchase();
                        break;
                    case R.id.exit:
                        finish();
                        break;
                    default:
                        //fab.show();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /**
     * Connects to the Google Play Billing service.
     * If successful, it queries for existing purchases.
     */
    private void connectToBillingService() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing setup successful. Querying purchases...");
                    // Billing service is connected, now query for existing purchases
                    queryPurchases();
                } else {
                    Log.e(TAG, "Billing setup failed: " + billingResult.getDebugMessage());
                    showToast("Billing setup failed: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to Google Play by calling
                // the startConnection() method.
                Log.w(TAG, "Billing service disconnected. Attempting to reconnect...");
                // You might want to implement a retry logic here
            }
        });
    }

    /**
     * Queries for existing purchases of managed products (like "remove ads").
     */
    private void queryPurchases() {
        // It's recommended to call this on a background thread
        executorService.execute(() -> {
            billingClient.queryPurchasesAsync(
                    QueryPurchasesParams.newBuilder()
                            .setProductType(BillingClient.ProductType.INAPP) // For managed products
                            .build(),
                    new PurchasesResponseListener() {
                        @Override
                        public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> purchases) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                Log.d(TAG, "Query purchases successful. Found " + purchases.size() + " purchases.");
                                // Process the list of purchases
                                processPurchases(purchases);
                            } else {
                                Log.e(TAG, "Query purchases failed: " + billingResult.getDebugMessage());
                                showToast("Failed to query purchases: " + billingResult.getDebugMessage());
                            }
                        }
                    }
            );
        });
    }

    /**
     * Processes a list of purchases to check for the "remove ads" product.
     *
     * @param purchases The list of current purchases for the user.
     */
    private void processPurchases(List<Purchase> purchases) {
        boolean adsRemoved = false;
        for (Purchase purchase : purchases) {
            // Check if the purchase is for our "remove ads" product
            if (purchase.getProducts().contains(REMOVE_ADS_PRODUCT_ID) &&
                    purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                adsRemoved = true;
                // Acknowledge the purchase if it hasn't been already
                if (!purchase.isAcknowledged()) {
                    acknowledgePurchase(purchase);
                }
                break; // Found the "remove ads" purchase, no need to check others
            }
        }
        // Update the ad status in MyAdManager and refresh UI
        boolean finalAdsRemoved = adsRemoved;
        runOnUiThread(() -> {
            adManager.setAdsDisabled(finalAdsRemoved);
            //updateAdStatusUI();
            //loadBannerAd(); // Reload or hide banner based on new status
        });
    }

    /**
     * Acknowledges a purchase. This is required for managed products.
     *
     * @param purchase The purchase to acknowledge.
     */
    private void acknowledgePurchase(Purchase purchase) {
        billingClient.acknowledgePurchase(
                AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build(),
                billingResult -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        Log.d(TAG, "Purchase acknowledged successfully.");
                        showToast("Purchase acknowledged!");
                    } else {
                        Log.e(TAG, "Failed to acknowledge purchase: " + billingResult.getDebugMessage());
                        showToast("Failed to acknowledge purchase: " + billingResult.getDebugMessage());
                    }
                }
        );
    }

    /**
     * Initiates the purchase flow for the "remove ads" product.
     */
    // In MainActivity.java

    /**
     * Initiates the purchase flow for the "remove ads" product.
     */
    private void initiateRemoveAdsPurchase() {
        if (!billingClient.isReady()) {
            showToast("Billing service not ready. Please try again in a moment.");
            Log.e(TAG, "BillingClient not ready.");
            return;
        }

        // Query product details before launching the flow
        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        productList.add(QueryProductDetailsParams.Product.newBuilder()
                .setProductId(REMOVE_ADS_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP) // For managed products (one-time)
                .build());

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        billingClient.queryProductDetailsAsync(params, new ProductDetailsResponseListener() {
            @Override
            public void onProductDetailsResponse(@NonNull BillingResult billingResult, @NonNull QueryProductDetailsResult result) {
                List<ProductDetails> productDetailsList = result.getProductDetailsList();

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && productDetailsList != null && !productDetailsList.isEmpty()) {
                    ProductDetails productDetails = productDetailsList.get(0);

                    BillingFlowParams.ProductDetailsParams productDetailsParams =
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                    .setProductDetails(productDetails)
                                    .build();

                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                            .setProductDetailsParamsList(Collections.singletonList(productDetailsParams))
                            .build();

                    billingClient.launchBillingFlow(MainActivity.this, billingFlowParams);
                } else {
                    Log.e(TAG, "Failed to get product details: " + billingResult.getDebugMessage());
                    showToast("Failed to get product details: " + billingResult.getDebugMessage());
                }
            }
        });
    }

    /**
     * Callback from Google Play Billing when purchases are updated (e.g., after a purchase).
     */
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            Log.d(TAG, "Purchases updated. Processing " + purchases.size() + " purchases.");
            processPurchases(purchases); // Re-process all purchases
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "User canceled the purchase.");
            showToast("Purchase canceled.");
        } else {
            Log.e(TAG, "Purchase failed or invalid: " + billingResult.getDebugMessage());
            showToast("Purchase failed: " + billingResult.getDebugMessage());
        }
    }

    /**
     * Loads the AdMob banner ad if ads are not disabled.
     * Hides the ad view if ads are disabled.
     */

    /**
     * Updates the UI elements to reflect the current ad status.
     */

    /**
     * Displays a short Toast message to the user.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disconnect from billing service when activity is destroyed
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
            Log.d(TAG, "Billing client disconnected.");
        }
        // Shut down the executor service
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
    private void setupEdgeToEdge() {
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightNavigationBars(false);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        Window window = getWindow();

        // Set status bar color as per theme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Prevent Android from forcing white nav background for 3-button navigation
            window.setNavigationBarContrastEnforced(false);

            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                // Adjust navigation bar icons (light/dark) based on your theme
                controller.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                );
            }
        } else {
            // For Android 10 and below → ensure nav bar is edge-to-edge
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }
}
