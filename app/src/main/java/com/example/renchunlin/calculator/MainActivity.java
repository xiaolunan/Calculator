package com.example.renchunlin.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 计算器
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_editText) //输入框
    EditText etEditText;
    @Bind(R.id.btn_clear)  //清空
    Button btnClear;
    @Bind(R.id.btn_delete)  //删除
    Button btnDelete;
    @Bind(R.id.btn_divide)  // 除以
    Button btnDivide;
    @Bind(R.id.btn_multiply)    // 乘以
    Button btnMultiply;
    @Bind(R.id.btn_7)
    Button btn7;
    @Bind(R.id.btn_8)
    Button btn8;
    @Bind(R.id.btn_9)
    Button btn9;
    @Bind(R.id.btn_reduce)  // 减去
    Button btnReduce;
    @Bind(R.id.btn_4)
    Button btn4;
    @Bind(R.id.btn_5)
    Button btn5;
    @Bind(R.id.btn_6)
    Button btn6;
    @Bind(R.id.btn_plus)    // 加
    Button btnPlus;
    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.btn_0)
    Button btn0;
    @Bind(R.id.btn_spot)    // 小数点
    Button btnSpot;
    @Bind(R.id.btn_equal)   // 等于
    Button btnEqual;
    private double dou;     //接收结果

    /**
     * 加个标识
     * 运算出结果的时候，再次点击数字，这个时候，输入框应该自动为空
     */
    private boolean flag;  //清空标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_editText, R.id.btn_clear, R.id.btn_delete, R.id.btn_divide, R.id.btn_multiply, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_reduce, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_plus, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_0, R.id.btn_spot, R.id.btn_equal})
    //业务逻辑
    public void onViewClicked(View view) {
        String str=etEditText.getText().toString();
        switch (view.getId()) {
            //点击数字键，输入框就接收数字
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_spot:
                //再次输入的时候判断，把前面的清空，再把点击的按钮放在输入框上
                if (flag) {
                    flag = false;
                    str = "";
                    etEditText.setText("");
                }
                //点击相应的数字添加在EditText上
                etEditText.setText(str + ((Button) view).getText());
                break;
            //清除按钮，直接把输入框设置为空
            case R.id.btn_clear:
                if(!TextUtils.isEmpty(str)){
                    flag = false;
                    str = "";
                    etEditText.setText("");
                }else{
                    Toast.makeText(this, "已空了！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete:
                //再次输入的时候判断，把前面的清空，再把点击的按钮放在输入框上
                if (flag) {
                    flag = false;
                    str = "";
                    etEditText.setText("");
                }
                //判断，因为需要一个个删，首先确定是不是为空
                else if(!TextUtils.isEmpty(str)){
                    //用substring()截取字符长度-1 再设置回去 形成删一个的效果
                    etEditText.setText(str.substring(0, str.length() - 1));
                }else{
                    Toast.makeText(this, "已空了！", Toast.LENGTH_SHORT).show();
                }
                break;
            //添加运算符
            case R.id.btn_divide:
            case R.id.btn_multiply:
            case R.id.btn_reduce:
            case R.id.btn_plus:
                //再次输入的时候判断，把前面的清空，再把点击的按钮放在输入框上
                if (flag) {
                    flag = false;
                    str = "";
                    etEditText.setText("");
                }
                //为了计算方便，在前后添加空格
                etEditText.setText(str +" "+ ((Button) view).getText()+" ");
                break;
            //计算结果
            case R.id.btn_equal:
                getResult();
                break;
        }
    }

    //等号计算结果
    private void getResult() {
        //取输入完后输入框的内容
        String result=etEditText.getText().toString();
        //当我们的输入框是null或者""时我们不进行操作
        if(result==null||result.equals("")){
            return;
        }
        //要计算结果，必须知道输入框是否有操作符，判断条件就是前后是否在空格，否则不进行操作
        if(!result.contains(" ")){
            //没有运算符，不运算
            return;
        }
        //当我点击等号的时候，清空标识设置为true,但是如果之前有的话，设置成false
        if (flag) {
            flag = false;
            return;
        }
        flag = true;
        //如果有空格，我们就截取前后段再获取运算符进行计算
        String str1=result.substring(0,result.indexOf(" "));//运算符前面字段
        String op=result.substring(result.indexOf(" ")+1,result.indexOf(" ")+2);//截取运算符
        String str2=result.substring(result.indexOf(" ")+3);//运算符后面字段
        //第一种情况：判断str1和str2都不是空
        if(!str1.equals("")&&!str2.equals("")){
            //强转
            double d1=Double.parseDouble(str1);
            double d2=Double.parseDouble(str2);
            //开始计算，定义一个double变量接收结果，定义为全局
            if(op.equals("+")){
                dou=d1+d2;
            }else if(op.equals("-")){
                dou=d1-d2;
            }else if(op.equals("*")){
                dou=d1*d2;
            }else if(op.equals("/")){
                //除数为0不计算
                if(d2==0){
                    d2=0;
                }else{
                    dou=d1/d2;
                }
            }
            //我们之前把他强转为double类型了，但是如果没有小数点，我们就是int类型
            if(!str1.equals(".")&&!str2.equals(".")&& !op.equals("÷")){
                int i= (int) dou;
                etEditText.setText(i+"");
            }else{
                //如果有小数点
                etEditText.setText(dou+"");
            }
            //第二种情况:str2是空
        }else if(!str1.equals("")&&str2.equals("")){
            //这种情况就不运算了
            etEditText.setText(result);
            //第三种情况:str1是空
        }else if(str1.equals("")&&!str2.equals("")){
            //这种情况我们就需要判断了，因为此时str1 = 0;
            double d3=Double.parseDouble(str2);
            if(op.equals("+")){
                dou=0+d3;
            }else if(op.equals("-")){
                dou=0-d3;
            }else if(op.equals("*")){
                dou=0;
            }else if(op.equals("/")){
                //除数为0不计算
                dou=0;
            }
            if(!str2.equals(".")){
                int i= (int) dou;
                etEditText.setText(i+"");
            }else{
                etEditText.setText(dou+"");
            }
            //最后一种情况，他们两个都是空
        }else{
            //清空
            etEditText.setText("");
        }
    }
}
