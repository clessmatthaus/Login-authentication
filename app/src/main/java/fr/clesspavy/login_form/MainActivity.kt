package fr.clesspavy.login_form
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_inscription.*
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        Enregistrement.setOnClickListener {
            var intent = Intent(this, Inscription::class.java)
            startActivity(intent)
            finish() }
        Identity.setOnClickListener {
           if(checking()) {
               val email = Email.text.toString()
               val password = mdp.text.toString()
               auth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this){
                   task-> if (task.isSuccessful) {
                      var intent = Intent(this,BaseIn::class.java)
                       intent.putExtra("email", email)
                       startActivity(intent)
                       finish()
               }else
               {
                   Toast.makeText(this, "Identifiant ou mot de passe incorrect...",
                       Toast.LENGTH_LONG).show()
               }

               }
           }
           else{
            Toast.makeText(this, "Veuillez renseigner votre identifiant et mot de passe",
                Toast.LENGTH_LONG).show()
           }
        }
    }
    private fun  checking(): Boolean {
        if (Email.text.toString().trim{it<=' '}.isNotEmpty() && mdp.text.toString().trim{it<=' '}
                .isNotEmpty())
        {
            return true
        }
        return false
    }
}