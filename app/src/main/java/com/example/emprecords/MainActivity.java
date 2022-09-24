package com.example.emprecords;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;//creating instances of db
    EditText editName, editGender, editSalary,editId;
    Button btnInsert, btnView,btnUpdate,btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editName);
        editGender = (EditText) findViewById(R.id.editGender);
        editSalary = (EditText) findViewById(R.id.editSalary);
        editId = (EditText) findViewById(R.id.editId);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),View.class);
                startActivity(i);
            }
        });

        btnUpdate= (Button)findViewById(R.id.btnUpdate);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void AddData() {
        btnInsert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editGender.getText().toString(), editSalary.getText().toString());
                        if (isInserted == true)//to check data is inserted ?
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();//obj
                        if (res.getCount() == 0) {//no data
                            showMessage("Error", "Nothing found");//message if no data found
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();                //instance if there is data
                        while (res.moveToNext()) {                              //move cursor to nxt result
                            buffer.append("Id :" + res.getString(0) + "\n");   //storing then result to buffer
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Gender :" + res.getString(2) + "\n");
                            buffer.append("Salary :" + res.getString(3) + "\n\n");
                        }

                        // Show all data if found
                        showMessage("Data", buffer.toString());//convert buffer to string
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//to alert dailog cretaing instance
        builder.setCancelable(true);//to cancel it after use
        builder.setTitle(title);//set title
        builder.setMessage(Message);//set message
        builder.show();//show alert
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editId.getText().toString(), editName.getText().toString(),
                                editGender.getText().toString(),editSalary.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());//taking obj of dbhelper
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}