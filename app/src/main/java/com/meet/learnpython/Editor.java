package com.meet.learnpython;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;

public class Editor extends AppCompatActivity {
    String code,output;
    AdView mAdview,mAdview1;
    AdRequest adRequest;
    private MyAdManager adManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupEdgeToEdge();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Editor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout rootLayout = findViewById(R.id.editor);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            view.setPadding(systemBars.left,0,systemBars.right, systemBars.bottom);

            Insets appbarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            appBarLayout.setPadding(0,appbarInsets.top,0,0);

            return WindowInsetsCompat.CONSUMED;
        });

        mAdview = (AdView) findViewById(R.id.adView);
        mAdview1 = (AdView)findViewById(R.id.adView1);

        adManager = new MyAdManager(this);
        loadBannerAd();

        if (!adManager.hasPurchasedRemoveAds()) {
            int paddingInDp = 50;
            int paddingInPx = dpToPx(this, paddingInDp);
            ScrollView sc = (ScrollView) findViewById(R.id.scrollView);
            sc.setPadding(0,0,0,paddingInPx);
        }else{
            int paddingInDp = 7;
            int paddingInPx = dpToPx(this, paddingInDp);
            ScrollView sc = (ScrollView) findViewById(R.id.scrollView);
            sc.setPadding(0,0,0,paddingInPx);
        }

        Intent i = getIntent();
        String str= i.getExtras().getString("key");
        String str1= i.getExtras().getString("key1");
        String str2= i.getExtras().getString("key2");

        if(str.equals("0") && str1.equals("0")){
            code="print(\"Hello, World!\")";
            output="Hello, World!";
        }else if(str.equals("0") && str1.equals("1")){
            code="A=10 #value of A is 10 and this is single line comment\n" +
                    "B=20\n" +
                    "print (A+B)";
            output="30";
        }else if(str.equals("0") && str1.equals("2")){
            code="\"\"\"This is multi line\n" +
                    "Comment in python\n" +
                    "In this comment Triple double quote is use\"\"\"\n" +
                    "A=10 \n" +
                    "B=20\n" +
                    "print (A+B)\n";
            output="30";
        }else if(str.equals("0") && str1.equals("3")){
            code="'''This is multi line\n" +
                    "Comment in python\n" +
                    "In this comment Triple single quote is use '''\n" +
                    "A=10 \n" +
                    "B=20\n" +
                    "print (A+B)";
            output="30";
        }else if(str.equals("0") && str1.equals("4")){
            code="\"\"\"This is a\n" +
                    "multiline docstring.\"\"\"\n" +
                    "print(\"Hello, World!\")";
            output="Hello, World!";
        }else if(str.equals("1") && str1.equals("0")){
            code="a=10 # integer\n" +
                    "f=10.2 # float\n" +
                    "name='John' #string\n" +
                    "print (a)\n" +
                    "print (f)\n" +
                    "print (name)";
            output="10\n" +
                    "10.2\n" +
                    "John\n";
        }else if(str.equals("1") && str1.equals("1")){
            code="x = \"Learn\"\n" +
                    "y = \"Python\"\n" +
                    "z = \"Programming\"\n" +
                    "print(x, y, z)";
            output="Learn Python Programming";
        }else if(str.equals("1") && str1.equals("2")){
            code="x = \"Learn \"\n" +
                    "y = \"Python \"\n" +
                    "z = \"Programming\"\n" +
                    "print(x + y + z)\n";
            output="Learn Python Programming";
        }else if(str.equals("2") && str1.equals("0")){
            code="a = 10 #integer type\n" +
                    "print(a)";
            output="10";
        }else if(str.equals("2") && str1.equals("1")){
            code="a = 10.12 #float type\n" +
                    "print(a)";
            output="10.12";
        }else if(str.equals("2") && str1.equals("2")){
            code="a= 10+0j #complex type\n" +
                    "print(a)";
            output="(10+0j)";
        }else if(str.equals("2") && str1.equals("3")){
            code="x = 20e3\n" +
                    "y = 10E4\n" +
                    "\n" +
                    "print(x)\n" +
                    "print(y)";
            output="20000.0\n" +
                    "100000.0";
        }else if(str.equals("2") && str1.equals("4")){
            code="str = 'Hello Python'\n" +
                    "print (str)";
            output="Hello Python";
        }else if(str.equals("2") && str1.equals("5")){
            code="a = 10 #integer type\n" +
                    "print(a)\n" +
                    "print(type(a))\n" +
                    "\n" +
                    "a = 20 #integer type\n" +
                    "print(a)\n" +
                    "print(type(a))\n" +
                    "\n" +
                    "a = 10.12 #float type\n" +
                    "print(a)\n" +
                    "print(type(a))\n" +
                    "\n" +
                    "a= 10+0j #complex type\n" +
                    "print(a)\n" +
                    "print(type(a))";
            output="10\n" +
                    "<class 'int'>\n" +
                    "20\n" +
                    "<class 'int'>\n" +
                    "10.12\n" +
                    "<class 'float'>\n" +
                    "(10+0j)\n" +
                    "<class 'complex' >";
        }else if(str.equals("2") && str1.equals("6")){
            code="x = int(4)\n" +
                    "y = int(6.9)\n" +
                    "z = int(\"6\")\n" +
                    "print(x)\n" +
                    "print(y)\n" +
                    "print(z)\n";
            output="4\n" +
                    "6\n" +
                    "6";
        }else if(str.equals("2") && str1.equals("7")){
            code="x = float(2)\n" +
                    "y = float(3.9)\n" +
                    "z = float(\"5\")\n" +
                    "w = float(\"7.3\")\n" +
                    "print(x)\n" +
                    "print(y)\n" +
                    "print(z)\n" +
                    "print(w)\n";
            output="2.0\n" +
                    "3.9\n" +
                    "5.0\n" +
                    "7.3";
        }else if(str.equals("2") && str1.equals("8")){
            code="x = str(\"p123\")\n" +
                    "y = str(3)\n" +
                    "z = str(5.0)\n" +
                    "print(x)\n" +
                    "print(y)\n" +
                    "print(z)";
            output="p123\n" +
                    "3\n" +
                    "5.0";
        }else if(str.equals("3") && str1.equals("0")){
            code="x = 10\n" +
                    "y = 20\n" +
                    "\n" +
                    "print(x + y)\n";
            output="30";
        }else if(str.equals("3") && str1.equals("1")){
            code="x = 10\n" +
                    "y = 5\n" +
                    "\n" +
                    "print(x - y)\n";
            output="5";
        }else if(str.equals("3") && str1.equals("2")){
            code="x = 10\n" +
                    "y = 5\n" +
                    "\n" +
                    "print(x * y)\n";
            output="50";
        }else if(str.equals("3") && str1.equals("3")){
            code="x = 10\n" +
                    "y = 2\n" +
                    "\n" +
                    "print(x / y)";
            output="5.0";
        }else if(str.equals("3") && str1.equals("4")){
            code="x = 5\n" +
                    "y = 2\n" +
                    "\n" +
                    "print(x % y)\n";
            output="1";
        }else if(str.equals("3") && str1.equals("5")){
            code="a=10\n" +
                    "b=5\n" +
                    "\n" +
                    "print(\"Addition = \",(a+b));\n" +
                    "print(\"Substraction = \",(a-b));\n" +
                    "print(\"Multiplication = \",(a*b));\n" +
                    "print(\"Division = \",(a/b));\n" +
                    "print(\"Exponent = \",(a**b));\n" +
                    "print(\"Floor division = \",(a//b));";
            output="Addition = 15\n" +
                    "Substraction = 5\n" +
                    "Multiplication = 50\n" +
                    "Division = 2.0\n" +
                    "Exponent = 100000\n" +
                    "Floor division = 2";
        }else if(str.equals("3") && str1.equals("6")){
            code="a=10\n" +
                    "b=5\n" +
                    "\n" +
                    "print (a==b);\n" +
                    "print (a!=b);\n" +
                    "print (a>b);\n" +
                    "print (a<b);\n" +
                    "print (a>=b);\n" +
                    "print (a<=b);\n";
            output="False\n" +
                    "True\n" +
                    "True\n" +
                    "False\n" +
                    "True\n" +
                    "False";
        }else if(str.equals("3") && str1.equals("7")){
            code="a = 10\n" +
                    "b = 5\n" +
                    "c = 0\n" +
                    "\n" +
                    "c = a + b\n" +
                    "print (\"c=a+b=\", c);\n" +
                    "\n" +
                    "c += a\n" +
                    "print (\"c=c+a=\", c); \n" +
                    "\n" +
                    "c *= a\n" +
                    "print (\"c=c*a=\", c); \n" +
                    "\n" +
                    "c /= a \n" +
                    "print (\"c=c/a=\", c); \n" +
                    "\n" +
                    "c %= a\n" +
                    "print (\"c=c%a=\", c); \n" +
                    "\n" +
                    "c=2\n" +
                    "c **= a\n" +
                    "print (\"c=c**a=\", c);\n" +
                    "\n" +
                    "c //= a\n" +
                    "print (\"c=c//a=\", c);";
            output="c=a+b= 15\n" +
                    "c=c+a= 25\n" +
                    "c=c*a= 250\n" +
                    "c=c/a= 25.0\n" +
                    "c=c%a= 5.0\n" +
                    "c=c**a= 1024\n" +
                    "c=c//a= 102";
        }else if(str.equals("3") && str1.equals("8")){
            code="a = 5\n" +
                    "b = 6\n" +
                    "\n" +
                    "print((a > 2) and (b >= 6))";
            output="True";
        }else if(str.equals("3") && str1.equals("9")){
            code="a = 10\n" +
                    "b = 5\n" +
                    "l = [1, 2, 3, 4, 5 ];\n" +
                    "\n" +
                    "if ( a in l ):\n" +
                    "   print (\"true\")\n" +
                    "else:\n" +
                    "   print (\"false\")\n" +
                    "   \n" +
                    "if ( a not in l ):\n" +
                    "   print (\"true\")\n" +
                    "else:\n" +
                    "   print (\"false\")\n" +
                    "  \n" +
                    "if ( b in l ):\n" +
                    "   print (\"true\")\n" +
                    "else:\n" +
                    "   print (\"false\")";
            output="false\n" +
                    "true\n" +
                    "true";
        }else if(str.equals("3") && str1.equals("10")){
            code="a = 10\n" +
                    "b = 5\n" +
                    "\n" +
                    "if ( a is b ):\n" +
                    "   print (\"true\")\n" +
                    "else:\n" +
                    "   print (\"false\")\n" +
                    "\n" +
                    "if ( a is not b ):\n" +
                    "   print (\"true\")\n" +
                    "else:\n" +
                    "   print (\"false\")";
            output="false\n" +
                    "true";
        }else if(str.equals("4") && str1.equals("0")){
            code="str = 'Hello Python'\n" +
                    "\n" +
                    "print (str)     ";
            output="Hello Python";
        }else if(str.equals("4") && str1.equals("1")){
            code="a = \"Hello, World!\"\n" +
                    "print(len(a))\n";
            output="13";
        }else if(str.equals("4") && str1.equals("2")){
            code="a = \"Hello, World!\"\n" +
                    "print(a.lower())";
            output="hello, world!";
        }else if(str.equals("4") && str1.equals("3")){
            code="a = \"Hello, World!\"\n" +
                    "print(a.upper())\n";
            output="HELLO, WORLD!";
        }else if(str.equals("4") && str1.equals("4")){
            code="str = 'Hello'\n" +
                    "str1 = 'Python'\n" +
                    "\n" +
                    "print(str+str1)";
            output="HelloPython";
        }else if(str.equals("4") && str1.equals("5")){
            code="str = 'Hello'\n" +
                    "\n" +
                    "print (str * 3)";
            output="HelloHelloHello";
        }else if(str.equals("4") && str1.equals("6")){
            code="str = 'Hello Python'\n" +
                    "\n" +
                    "print (str[0])      \n" +
                    "print (str[1:4])\n" +
                    "print (str[4:])";
            output="H\n" +
                    "ell\n" +
                    "o Python";
        }else if(str.equals("4") && str1.equals("7")){
            code="str = \"Hello\"\n" +
                    "\n" +
                    "print(str.replace(\"H\", \"i\"))";
            output="iello";
        }else if(str.equals("4") && str1.equals("8")){
            code="str = \"Hello, Python\"\n" +
                    "\n" +
                    "print(str.split(\",\"))";
            output="['Hello', ' Python']";
        }else if(str.equals("5") && str1.equals("0")){
            code="a=10\n" +
                    "if (a>=10):\n" +
                    "    print (\"value of a is greater than or equal to 10\")";
            output="value of a is greater than or equal to 10";
        }else if(str.equals("5") && str1.equals("1")){
            code="a=10\n" +
                    "if (a>=10):\n" +
                    "    print (\"value of a is greater than or equal to 10\")\n" +
                    "else:\n" +
                    "    print (\"value of a is less than 10\")";
            output="value of a is greater than or equal to 10";
        }else if(str.equals("5") && str1.equals("2")){
            code="a=10\n" +
                    "b=20\n" +
                    "if (a>b):\n" +
                    "    print (\"value of a is greater than b\")\n" +
                    "elif (a<b):\n" +
                    "    print (\"value of a is less than b\")\n" +
                    "else:\n" +
                    "    print (\"a is equal to b\")    ";
            output="value of a is less than b";
        }else if(str.equals("5") && str1.equals("3")){
            code="a=51\n" +
                    "if (a<100):\n" +
                    "    if (a>50):\n" +
                    "        print (\"value of a is greater than 50\")\n" +
                    "    else:\n" +
                    "        print (\"value of a is less than 50\")\n" +
                    "else:\n" +
                    "    print(\"value of a is greater than 100\")      \n";
            output="value of a is greater than 50";
        }else if(str.equals("5") && str1.equals("4")){
            code="a = 60\n" +
                    "b = 12\n" +
                    "\n" +
                    "if a > b: print(\"a is greater than b\")";
            output="a is greater than b";
        }else if(str.equals("5") && str1.equals("5")){
            code="a = 50\n" +
                    "b = 250\n" +
                    "\n" +
                    "print(\"A\") if a > b else print(\"B\")\n";
            output="B";
        }else if(str.equals("5") && str1.equals("6")){
            code="a = 200\n" +
                    "b = 14\n" +
                    "c = 350\n" +
                    "if a > b and c > a:\n" +
                    "  print(\"Both conditions are True\")";
            output="Both conditions are True";
        }else if(str.equals("5") && str1.equals("7")){
            code="a = 200\n" +
                    "b = 33\n" +
                    "c = 500\n" +
                    "if a > b or a > c:\n" +
                    "  print(\"At least one of the conditions is True\")\n";
            output="At least one of the conditions is True";
        }else if(str.equals("6") && str1.equals("0")){
            code="i = 1\n" +
                    "while i < 6:\n" +
                    "  print(i)\n" +
                    "  i += 1";
            output="1\n" +
                    "2\n" +
                    "3\n" +
                    "4\n" +
                    "5";
        }else if(str.equals("6") && str1.equals("1")){
            code="a=0\n" +
                    "for a in range(0,5):  \n" +
                    "    print (\"Value of a is\",a) ";
            output="Value of a is 0\n" +
                    "Value of a is 1\n" +
                    "Value of a is 2\n" +
                    "Value of a is 3\n" +
                    "Value of a is 4";
        }else if(str.equals("6") && str1.equals("2")){
            code="a=0\n" +
                    "while (a<5):\n" +
                    "    print (a)\n" +
                    "    a=a+1\n" +
                    "else:\n" +
                    "    print (\"Value of a is\",a)\n";
            output="0\n" +
                    "1\n" +
                    "2\n" +
                    "3\n" +
                    "4\n" +
                    "Value of a is 5";
        }else if(str.equals("6") && str1.equals("3")){
            code="for x in range(6):\n" +
                    "  print(x)\n" +
                    "else:\n" +
                    "  print(\"Finally finished!\")";
            output="0\n" +
                    "1\n" +
                    "2\n" +
                    "3\n" +
                    "4\n" +
                    "5\n" +
                    "Finally finished!";
        }else if(str.equals("6") && str1.equals("4")){
            code="i = 1\n" +
                    "while i < 6:\n" +
                    "  print(i)\n" +
                    "  if (i == 3):\n" +
                    "    break\n" +
                    "  i += 1\n";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("6") && str1.equals("5")){
            code="i = 0\n" +
                    "while i < 6:\n" +
                    "  i += 1\n" +
                    "  if i == 3:\n" +
                    "    continue\n" +
                    "  print(i)";
            output="1\n" +
                    "2\n" +
                    "4\n" +
                    "5\n" +
                    "6";
        }else if(str.equals("6") && str1.equals("6")){
            code="y = [1,2,3,4,5]\n" +
                    "for x in y:\n" +
                    "  print(x) \n" +
                    "  if x == 3:\n" +
                    "    break";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("6") && str1.equals("7")){
            code="y = [1,2,3,4,5]\n" +
                    "for x in y:\n" +
                    "  if x == 3:\n" +
                    "    continue\n" +
                    "  print(x) \n";
            output="1\n" +
                    "2\n" +
                    "4\n" +
                    "5";
        }else if(str.equals("6") && str1.equals("8")){
            code="list =[10,20,30,40,50]\n" +
                    "list1 = [10]\n" +
                    "for a in list: \n" +
                    "    for b in list1:\n" +
                    "        print ('%d+%d = %d'%(a,b,a+b))";
            output="10+10 = 20\n" +
                    "20+10 = 30\n" +
                    "30+10 = 40\n" +
                    "40+10 = 50\n" +
                    "50+10 = 60";
        }else if(str.equals("7") && str1.equals("0")){
            code="thislist = [1, 2, 3]\n" +
                    "print(thislist)";
            output="[1, 2, 3]";
        }else if(str.equals("7") && str1.equals("1")){
            code="thislist = [1, 2, 3]\n" +
                    "print(thislist[1])";
            output="2";
        }else if(str.equals("7") && str1.equals("2")){
            code="thislist = [1, 2, 3]\n" +
                    "thislist[1] = 4\n" +
                    "\n" +
                    "print(thislist)\n";
            output="[1, 4, 3]";
        }else if(str.equals("7") && str1.equals("3")){
            code="thislist = [1, 2, 3]\n" +
                    "for x in thislist:\n" +
                    "  print(x)\n";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("7") && str1.equals("4")){
            code="thislist = [1, 2, 3]\n" +
                    "if 2 in thislist:\n" +
                    "  print(\"Yes, 2 is in the list\")";
            output="Yes, 2 is in the list";
        }else if(str.equals("7") && str1.equals("5")){
            code="thislist = [1, 2, 3]\n" +
                    "print(len(thislist))\n";
            output="3";
        }else if(str.equals("7") && str1.equals("6")){
            code="thislist = [1, 2, 3]\n" +
                    "\n" +
                    "thislist.append(4)\n" +
                    "\n" +
                    "print(thislist)\n";
            output="[1, 2, 3, 4]";
        }else if(str.equals("7") && str1.equals("7")){
            code="thislist = [1,2,3]\n" +
                    "thislist.insert(1, 4)\n" +
                    "print(thislist)";
            output="[1, 4, 2, 3]";
        }else if(str.equals("7") && str1.equals("8")){
            code="thislist = [1, 2, 3]\n" +
                    "thislist.remove(2)\n" +
                    "print(thislist)";
            output="[1, 3]";
        }else if(str.equals("7") && str1.equals("9")){
            code="thislist = [1, 2, 3]\n" +
                    "thislist.pop()\n" +
                    "print(thislist)";
            output="[1, 2]";
        }else if(str.equals("7") && str1.equals("10")){
            code="thislist = [1, 2, 3]\n" +
                    "del thislist[0]\n" +
                    "print(thislist)";
            output="[2, 3]";
        }else if(str.equals("7") && str1.equals("11")){
            code="thislist = [1, 2, 3]\n" +
                    "thislist.clear()\n" +
                    "print(thislist)";
            output="[]";
        }else if(str.equals("7") && str1.equals("12")){
            code="list = [ 'abc', 123 , 'hi', 10.12 , 10 ]\n" +
                    "\n" +
                    "print (list[0])\n" +
                    "print (list[2:4])\n" +
                    "print (list[2:]) ";
            output="abc\n" +
                    "['hi', 10.12]\n" +
                    "['hi', 10.12, 10]";
        }else if(str.equals("7") && str1.equals("13")){
            code="list = [ 'abc', 123]\n" +
                    "\n" +
                    "print (list*2) ";
            output="['abc', 123, 'abc', 123]";
        }else if(str.equals("8") && str1.equals("0")){
            code="thistuple = (1, 2, 3)\n" +
                    "print(thistuple)\n";
            output="(1, 2, 3)";
        }else if(str.equals("8") && str1.equals("1")){
            code="thistuple = (1, 2, 3)\n" +
                    "print(thistuple[1])";
            output="2";
        }else if(str.equals("8") && str1.equals("2")){
            code="thistuple = (1, 2, 3)\n" +
                    "for x in thistuple:\n" +
                    "  print(x)\n";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("8") && str1.equals("3")){
            code="thistuple = (1, 2, 3)\n" +
                    "if 2 in thistuple:\n" +
                    "  print(\"Yes, 2 is in the tuple\")";
            output="Yes, 2 is in the tuple";
        }else if(str.equals("8") && str1.equals("4")){
            code="thistuple = (1, 2, 3)\n" +
                    "print(len(thistuple))";
            output="3";
        }else if(str.equals("8") && str1.equals("5")){
            code="tuple = ( 'abc', 123 , 'hi', 10.12 , 10 )\n" +
                    "\n" +
                    "print (tuple[0])       \n" +
                    "print (tuple[2:4])      \n" +
                    "print (tuple[3:])      \n" +
                    "print (tuple[-3:-1])";
            output="abc\n" +
                    "('hi', 10.12)\n" +
                    "(10.12, 10)\n" +
                    "('hi', 10.12)";
        }else if(str.equals("8") && str1.equals("6")){
            code="tuple = ( 'abc', 123 )\n" +
                    "\n" +
                    "print (tuple*2)";
            output="('abc', 123, 'abc', 123)";
        }else if(str.equals("9") && str1.equals("0")){
            code="thisset = {1, 2, 3}\n" +
                    "print(thisset)\n";
            output="{1, 2, 3}";
        }else if(str.equals("9") && str1.equals("1")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "for x in thisset:\n" +
                    "  print(x)\n";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("9") && str1.equals("2")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "print(2 in thisset)";
            output="True";
        }else if(str.equals("9") && str1.equals("3")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "thisset.add(4)\n" +
                    "\n" +
                    "print(thisset)";
            output="{1, 2, 3, 4}";
        }else if(str.equals("9") && str1.equals("4")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "thisset.update([4, 5, 6])\n" +
                    "\n" +
                    "print(thisset)\n";
            output="{1, 2, 3, 4, 5, 6}";
        }else if(str.equals("9") && str1.equals("5")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "print(len(thisset))";
            output="3";
        }else if(str.equals("9") && str1.equals("6")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "thisset.remove(2)\n" +
                    "\n" +
                    "print(thisset)\n";
            output="{1,3}";
        }else if(str.equals("9") && str1.equals("7")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "thisset.discard(2)\n" +
                    "\n" +
                    "print(thisset)";
            output="{1, 3}";
        }else if(str.equals("9") && str1.equals("8")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "x = thisset.pop()\n" +
                    "\n" +
                    "print(x) \n" +
                    "\n" +
                    "print(thisset) \n";
            output="1\n" +
                    "{2, 3}";
        }else if(str.equals("9") && str1.equals("9")){
            code="thisset = {1, 2, 3}\n" +
                    "\n" +
                    "thisset.clear()\n" +
                    "\n" +
                    "print(thisset)";
            output="set()";
        }else if(str.equals("9") && str1.equals("10")){
            code="thisset = set((1, 2, 3))\n" +
                    "print(thisset)";
            output="{1, 2, 3}";
        }else if(str.equals("10") && str1.equals("0")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "print(thisdict)";
            output="{'fname': 'John', 'lname': 'Doe', 'dobyear': 1990}";
        }else if(str.equals("10") && str1.equals("1")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "x = thisdict[\"fname\"]\n" +
                    "print(x)";
            output="John";
        }else if(str.equals("10") && str1.equals("2")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "\n" +
                    "thisdict[\"dobyear\"] = 2000\n" +
                    "\n" +
                    "print(thisdict)";
            output="{'fname': 'John', 'lname': 'Doe', 'dobyear': 2000}";
        }else if(str.equals("10") && str1.equals("3")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "for x in thisdict:\n" +
                    "  print(x)\n";
            output="fname\n" +
                    "lname\n" +
                    "dobyear";
        }else if(str.equals("10") && str1.equals("4")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "for x in thisdict:\n" +
                    "  print(thisdict[x])\n";
            output="John\n" +
                    "Doe\n" +
                    "1990";
        }else if(str.equals("10") && str1.equals("5")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "for x in thisdict.values():\n" +
                    "  print(x)";
            output="John\n" +
                    "Doe\n" +
                    "1990";
        }else if(str.equals("10") && str1.equals("6")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "for x, y in thisdict.items():\n" +
                    "  print(x, y)\n";
            output="fname John\n" +
                    "lname Doe\n" +
                    "dobyear 1990";
        }else if(str.equals("10") && str1.equals("7")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "if \"fname\" in thisdict:\n" +
                    "  print(\"Yes, 'fname' is one of the keys in the thisdict dictionary\")";
            output="Yes, 'fname' is one of the keys in the thisdict dictionary";
        }else if(str.equals("10") && str1.equals("8")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "\n" +
                    "print(len(thisdict))";
            output="3";
        }else if(str.equals("10") && str1.equals("9")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "thisdict[\"gender\"] = \"male\"\n" +
                    "print(thisdict)\n";
            output="{'fname': 'John', 'lname': 'Doe', 'dobyear': 1990, 'gender': 'male'}";
        }else if(str.equals("10") && str1.equals("10")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "thisdict.pop(\"lname\")\n" +
                    "print(thisdict)";
            output="{'fname': 'John', 'dobyear': 1990}";
        }else if(str.equals("10") && str1.equals("11")){
            code="thisdict =\t{\n" +
                    "  \"fname\": \"John\",\n" +
                    "  \"lname\": \"Doe\",\n" +
                    "  \"dobyear\": 1990\n" +
                    "}\n" +
                    "thisdict.clear()\n" +
                    "print(thisdict)";
            output="{}";
        }else if(str.equals("10") && str1.equals("12")){
            code="thisdict = dict(fname=\"John\", lname=\"Doe\", year=1990)\n" +
                    "\n" +
                    "print(thisdict)\n";
            output="{'fname': 'John', 'lname': 'Doe', 'year': 1990}";
        }else if(str.equals("11") && str1.equals("0")){
            code="def my_function():\n" +
                    "  print(\"Hello from a function\")\n" +
                    "\n" +
                    "my_function()";
            output="Hello from a function";
        }else if(str.equals("11") && str1.equals("1")){
            code="def my_function(fname):\n" +
                    "  print(fname + \" Doe\")\n" +
                    "\n" +
                    "my_function(\"John\")\n" +
                    "my_function(\"Peter\")\n" +
                    "my_function(\"Kevin\")";
            output="John Doe\n" +
                    "Peter Doe\n" +
                    "Kevin Doe";
        }else if(str.equals("11") && str1.equals("2")){
            code="def my_function(country = \"Norway\"):\n" +
                    "  print(\"I am from \" + country)\n" +
                    "\n" +
                    "my_function(\"Sweden\")\n" +
                    "my_function(\"India\")\n" +
                    "my_function()\n" +
                    "my_function(\"Brazil\")";
            output="I am from Sweden\n" +
                    "I am from India\n" +
                    "I am from Norway\n" +
                    "I am from Brazil";
        }else if(str.equals("11") && str1.equals("3")){
            code="def my_function(x):\n" +
                    "  return 5 * x\n" +
                    "\n" +
                    "print(my_function(10))\n" +
                    "print(my_function(2))\n" +
                    "print(my_function(3))";
            output="50\n" +
                    "10\n" +
                    "15";
        }else if(str.equals("11") && str1.equals("4")){
            code="def tri_recursion(k):\n" +
                    "  if(k > 0):\n" +
                    "    result = k + tri_recursion(k - 1)\n" +
                    "    print(result)\n" +
                    "  else:\n" +
                    "    result = 0\n" +
                    "  return result\n" +
                    "\n" +
                    "print(\"Recursion Example Results\")\n" +
                    "tri_recursion(6)";
            output="Recursion Example Results\n" +
                    "1\n" +
                    "3\n" +
                    "6\n" +
                    "10\n" +
                    "15\n" +
                    "21";
        }else if(str.equals("11") && str1.equals("5")){
            code="x = lambda a: a + 10\n" +
                    "print(x(5))";
            output="15";
        }else if(str.equals("11") && str1.equals("6")){
            code="x = lambda a, b: a * b\n" +
                    "print(x(5, 6))\n";
            output="30";
        }else if(str.equals("11") && str1.equals("7")){
            code="x = lambda a, b, c: a + b + c\n" +
                    "print(x(5, 6, 2))";
            output="13";
        }else if(str.equals("12") && str1.equals("0")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "print(ary)";
            output="[1, 2, 3]";
        }else if(str.equals("12") && str1.equals("1")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "x = ary[0]\n" +
                    "\n" +
                    "print(x)\n";
            output="1";
        }else if(str.equals("12") && str1.equals("2")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "ary[1] = 4\n" +
                    "\n" +
                    "print(ary)\n";
            output="[1, 4, 3]";
        }else if(str.equals("12") && str1.equals("3")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "x = len(ary)\n" +
                    "\n" +
                    "print(x)";
            output="3";
        }else if(str.equals("12") && str1.equals("4")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "for x in ary:\n" +
                    "  print(x)";
            output="1\n" +
                    "2\n" +
                    "3";
        }else if(str.equals("12") && str1.equals("5")){
            code="ary = [1, 2, 3]\n" +
                    "\n" +
                    "ary.append(4)\n" +
                    "\n" +
                    "print(ary)\n";
            output="[1, 2, 3, 4]";
        }else if(str.equals("12") && str1.equals("6")){
            code="ary = [10, 20, 30]\n" +
                    "\n" +
                    "ary.pop(1)\n" +
                    "\n" +
                    "print(ary)\n";
            output="[10, 30]";
        }else if(str.equals("13") && str1.equals("0")){
            code="x = min(5, 10, 25)\n" +
                    "y = max(5, 10, 25)\n" +
                    "\n" +
                    "print(x)\n" +
                    "print(y)";
            output="5\n" +
                    "25";
        }else if(str.equals("13") && str1.equals("1")){
            code="x = abs(-7.25)\n" +
                    "\n" +
                    "print(x)\n";
            output="7.25";
        }else if(str.equals("13") && str1.equals("2")){
            code="x = pow(4, 3)\n" +
                    "\n" +
                    "print(x)\n";
            output="64";
        }else if(str.equals("13") && str1.equals("3")){
            code="x = pow(4, 3)\n" +
                    "\n" +
                    "print(x)\n";
            output="8.0";
        }else if(str.equals("13") && str1.equals("4")){
            code="import math\n" +
                    "\n" +
                    "x = math.ceil(1.4)\n" +
                    "\n" +
                    "y = math.floor(1.4)\n" +
                    "\n" +
                    "print(x)\n" +
                    "print(y)\n";
            output="2\n" +
                    "1";
        }else if(str.equals("13") && str1.equals("5")){
            code="import math\n" +
                    "\n" +
                    "x = math.pi\n" +
                    "\n" +
                    "print(x)\n";
            output="3.141592653589793";
        }else if(str.equals("14") && str1.equals("0")){
            code="class MyClass:\n" +
                    "  x = 5\n" +
                    "\n" +
                    "print(MyClass)";
            output="<class '__main__.MyClass'>";
        }else if(str.equals("14") && str1.equals("1")){
            code="class MyClass:\n" +
                    "  x = 5\n" +
                    "\n" +
                    "p1 = MyClass()\n" +
                    "print(p1.x)";
            output="5";
        }else if(str.equals("14") && str1.equals("2")){
            code="class Person:\n" +
                    "  def __init__(self, name, age):\n" +
                    "    self.name = name\n" +
                    "    self.age = age\n" +
                    "\n" +
                    "p1 = Person(\"John\", 36)\n" +
                    "\n" +
                    "print(p1.name)\n" +
                    "print(p1.age)";
            output="John\n" +
                    "36";
        }else if(str.equals("14") && str1.equals("3")){
            code="class Person:\n" +
                    "  def __init__(self, name, age):\n" +
                    "    self.name = name\n" +
                    "    self.age = age\n" +
                    "\n" +
                    "  def myfunc(self):\n" +
                    "    print(\"Hello my name is \" + self.name)\n" +
                    "\n" +
                    "p1 = Person(\"John\", 36)\n" +
                    "p1.myfunc()";
            output="Hello my name is John";
        }else if(str.equals("14") && str1.equals("4")){
            code="class Person:\n" +
                    "  def __init__(self, name, age):\n" +
                    "    self.name = name\n" +
                    "    self.age = age\n" +
                    "\n" +
                    "  def myfunc(self):\n" +
                    "    print(\"Hello my name is \" + self.name)\n" +
                    "\n" +
                    "p1 = Person(\"John\", 36)\n" +
                    "p1.myfunc()";
            output="Hello my name is John";
        }else if(str.equals("14") && str1.equals("5")){
            code="class Person:\n" +
                    "  def __init__(self, name, age):\n" +
                    "    self.name = name\n" +
                    "    self.age = age\n" +
                    "\n" +
                    "  def myfunc(self):\n" +
                    "    print(\"Hello my name is \" + self.name)\n" +
                    "\n" +
                    "p1 = Person(\"John\", 36)\n" +
                    "\n" +
                    "p1.age = 40\n" +
                    "\n" +
                    "print(p1.age)";
            output="40";
        }else if(str.equals("14") && str1.equals("6")){
            code="class abc:\n" +
                    "    def __init__(self):\n" +
                    "        print (\"Default Constructor\")\n" +
                    "        \n" +
                    "    def fun(self,a,b):\n" +
                    "        print (\"Value of A is\", a,\"and B is\", b)\n" +
                    "obj=abc()\n" +
                    "obj.fun(10,20)\n";
            output="Default Constructor\n" +
                    "Value of A is 10 and B is 20";
        }else if(str.equals("14") && str1.equals("7")){
            code="class abc:\n" +
                    "    def __init__(self,a,b):\n" +
                    "        print (\"Parameterized Constructor\")\n" +
                    "        self.a=a\n" +
                    "        self.b=b\n" +
                    "        \n" +
                    "    def fun(self):\n" +
                    "        print (\"Value of A is\", self.a,\"and B is\", self.b)\n" +
                    "obj=abc(10,20)\n" +
                    "obj.fun()\n";
            output="Parameterized Constructor\n" +
                    "Value of A is 10 and B is 20";
        }else if(str.equals("14") && str1.equals("8")){
            code="class A:\n" +
                    "    def __init__(self):\n" +
                    "        print('Hello Python')\n" +
                    "\n" +
                    "    def __del__(self):\n" +
                    "        print('Destructor called')\n" +
                    "\n" +
                    "obj = A()\n" +
                    "print('Hi Python')";
            output="Hello Python\n" +
                    "Hi Python\n" +
                    "Destructor called";
        }else if(str.equals("14") && str1.equals("9")){
            code="a=10\n" +
                    "b=20\n" +
                    "print (a+b)\n" +
                    "\n" +
                    "str1=\"Hello\"\n" +
                    "str2=\"Python\"\n" +
                    "print (str1+str2)\n";
            output="30\n" +
                    "HelloPython";
        }else if(str.equals("14") && str1.equals("10")){
            code="class A:\n" +
                    "    def a(self):\n" +
                    "        print (\"Hello Python\")\n" +
                    "class B(A):\n" +
                    "    def a(self):\n" +
                    "        print (\"Hi Python\")\n" +
                    "        \n" +
                    "objb=B()\n" +
                    "objb.a()\n";
            output="Hi Python";
        }else if(str.equals("15") && str1.equals("0")){
            code="class A:\n" +
                    "    def a(self):\n" +
                    "        print (\"Class A\")\n" +
                    "\n" +
                    "class B(A):\n" +
                    "    def b(self):\n" +
                    "        print (\"Class B\")\n" +
                    "        \n" +
                    "objb=B()\n" +
                    "objb.a()\n" +
                    "objb.b()\n";
            output="Class A\n" +
                    "Class B";
        }else if(str.equals("15") && str1.equals("1")){
            code="class A:\n" +
                    "    def a(self):\n" +
                    "        print (\"Class A\")\n" +
                    "\n" +
                    "class B():\n" +
                    "    def b(self):\n" +
                    "        print (\"Class B\")\n" +
                    "\n" +
                    "class C(A,B):\n" +
                    "    def c(self):\n" +
                    "        print (\"Class C\")\n" +
                    "        \n" +
                    "objc=C()\n" +
                    "objc.a()\n" +
                    "objc.b()\n" +
                    "objc.c()\n";
            output="Class A\n" +
                    "Class B\n" +
                    "Class C\n";
        }else if(str.equals("15") && str1.equals("2")){
            code="class A:\n" +
                    "    def a(self):\n" +
                    "        print (\"Class A\")\n" +
                    "\n" +
                    "class B(A):\n" +
                    "    def b(self):\n" +
                    "        print (\"Class B\")\n" +
                    "\n" +
                    "class C(B):\n" +
                    "    def c(self):\n" +
                    "        print (\"Class C\")\n" +
                    "        \n" +
                    "objb=B()\n" +
                    "objb.a()\n" +
                    "objb.b()\n" +
                    "\n" +
                    "objc=C()\n" +
                    "objc.a()\n" +
                    "objc.b()\n" +
                    "objc.c()\n";
            output="Class A\n" +
                    "Class B\n" +
                    "Class A\n" +
                    "Class B\n" +
                    "Class C";
        }else if(str.equals("15") && str1.equals("3")){
            code="class A:\n" +
                    "    def a(self):\n" +
                    "        print (\"Class A\")\n" +
                    "\n" +
                    "class B(A):\n" +
                    "    def b(self):\n" +
                    "        print (\"Class B\")\n" +
                    "\n" +
                    "class C(A):\n" +
                    "    def c(self):\n" +
                    "        print (\"Class C\")\n" +
                    "        \n" +
                    "objb=B()\n" +
                    "objb.a()\n" +
                    "objb.b()\n" +
                    "\n" +
                    "objc=C()\n" +
                    "objc.a()\n" +
                    "objc.c()\n";
            output="Class A\n" +
                    "Class B\n" +
                    "Class A\n" +
                    "Class C";
        }


        TextView label = findViewById(R.id.tx1);
        label.setText("Example of "+str2);

        WebView webView1 = (WebView) findViewById(R.id.simpleWebView1);
        webView1.getSettings().setJavaScriptEnabled(true);

        webView1.loadDataWithBaseURL(null, "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<style>\n" +
                "      body {\n" +
                "         background-color: white;\n" +
                "         color: black;" +
                "         font-size: 14px;" +
                "      }\n" +
                "</style>\n" +
                "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/prism/1.25.0/themes/prism-okaidia.min.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/prism/1.25.0/plugins/line-numbers/prism-line-numbers.min.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/prism/1.25.0/prism.min.js\"></script>\n" +
                "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/prism/1.25.0/plugins/line-numbers/prism-line-numbers.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/prism.js\"></script>"+
                "<pre><code class=\"language-python line-numbers\">"+
                code+
                "</code></pre>"+
                "        </body>\n" +
                "</html>", "text/html", "utf-8", null);


        Button bt = findViewById(R.id.button);
        TextView tx = findViewById(R.id.tx);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setText(output);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadBannerAd() {
        if (!adManager.hasPurchasedRemoveAds()) {
            // Ads are enabled, load the banner ad
            adRequest=new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
            mAdview.setVisibility(View.VISIBLE);

            adRequest=new AdRequest.Builder().build();
            mAdview1.loadAd(adRequest);
            mAdview1.setVisibility(View.VISIBLE);
        } else {
            // Ads are disabled, hide the banner ad
            mAdview.setVisibility(View.GONE);
            mAdview1.setVisibility(View.GONE);
        }
    }
    public int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
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