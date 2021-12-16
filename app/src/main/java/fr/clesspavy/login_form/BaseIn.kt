package fr.clesspavy.login_form

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_base_in.*

class BaseIn : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_in)
        val sharedPref=this.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin= sharedPref.getString("Email", "1")
        exit.setOnClickListener {
            sharedPref.edit().remove("Email").apply()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        db= FirebaseFirestore.getInstance()
        if(isLogin=="1")
        {
            val email = intent.getStringExtra("email")
            if(email!=null){
                setText(email)
                with(sharedPref.edit()){
                    putString("Email", email)
                    apply()
                }
            }else{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            setText(isLogin)

        }
    }
    private fun setText(email:String?)
    {
        db = FirebaseFirestore.getInstance()
        if(email !=null) {
            db.collection("USERS").document(email).get()
                .addOnSuccessListener {
                        tasks ->
                    nom.text = tasks.get("username").toString()
                    mobile.text = tasks.get("telephone").toString()
                    emailbase.text = tasks.get("email").toString()
                }

        }

    }
}