package org.example.andeli.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
   int jumlahKopi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void submitOrder(View view){
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();
        String subject = "Just Java Order for " + name;



        CheckBox oreoCreamCheckBox = (CheckBox) findViewById(R.id.oreo_check_Box);
        boolean hasOreo = oreoCreamCheckBox.isChecked();

        CheckBox bengCheckBox = (CheckBox) findViewById(R.id.beng_check_box);
        boolean hasBeng = bengCheckBox.isChecked();

        int price = calculatePrice(hasOreo,hasBeng);
        String pesanprice = createOrderSummary(name,price,hasOreo,hasBeng);



        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: annomaker@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, pesanprice);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayPesan(pesanprice);

    }

    private String createOrderSummary(String name,int price,boolean addOreo,boolean addBeng) {
       String pesanprice = getString(R.string.nama)+ name +
                           "\nJumlah : " + jumlahKopi +
                           "\n"+getString(R.string.pakaiOreo) +addOreo +
                           "\n"+getString(R.string.pakaiBeng) +addBeng +
                           "\n"+ getString(R.string.totalHarga) + price;
       pesanprice = pesanprice + "\n" + getString(R.string.terimakasih);

       return pesanprice;

    }




    private int calculatePrice(boolean addOreo,boolean addBeng) {
       int HargaAwal = 5000;
        //without coffe

       if(addOreo){
           HargaAwal = HargaAwal + 2000;
       }

       if(addBeng){
           HargaAwal = HargaAwal + 3000;
       }

       return jumlahKopi * HargaAwal;
    }


    public void increment(View view){
        jumlahKopi = jumlahKopi + 1;
        display(jumlahKopi);
    }

    public void decrement(View view){
        if(jumlahKopi == 1){
            Toast.makeText(this,"Kamu gak bisa beli koffe kurang dari 1",Toast.LENGTH_SHORT).show();
            return;
        }
        jumlahKopi = jumlahKopi - 1;
        display(jumlahKopi  );
    }


    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number );
    }


    private void displayPesan(String Pesan){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(Pesan);
    }


}
