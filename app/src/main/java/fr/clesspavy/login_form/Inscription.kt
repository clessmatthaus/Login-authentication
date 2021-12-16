package fr.clesspavy.login_form
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_inscription.*
import kotlinx.android.synthetic.main.activity_inscription.Buttons
import kotlinx.android.synthetic.main.activity_main.*
class Inscription : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        Buttons.setOnClickListener {
            if (checking()) {
                var email = emailinscription.text.toString()
                var password = mdpinscription.text.toString()
                var name = Username.text.toString()
                var phone = telephone.text.toString()
                val user = hashMapOf(
                    "Username" to name,
                    "telephone" to phone,
                    "email" to email,
                )
                val Users = db.collection("USERS")
val  query = Users.whereEqualTo("email", email).get().addOnSuccessListener {
    tasks->
    if(tasks.isEmpty)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                task->
                if(task.isSuccessful)
                {
                  Users.document(email).set(user)
                  val intent = Intent(this, BaseIn::class.java)
                    intent.putExtra("email", email)
                  startActivity(intent)
                  finish()
                }
                else
                {
                    Toast.makeText(this,
                        "authentification manquée....", Toast.LENGTH_LONG).show() } }
    }
    else
    {
        Toast.makeText(this,
            "les utilisateurs sont deja inscrits....",
            Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
            }
        else
            {
                Toast.makeText(this,
                    "les informations renseignées sont incorrectes....", Toast.LENGTH_LONG).show() }
        }
    }

    private fun checking():Boolean {
        if(Username.text.toString().trim{it<=' '}.isNotEmpty()
            && emailinscription.text.toString().trim{it<=' '}.isNotEmpty()
            && mdpinscription.text.toString().trim { it<=' '}.isNotEmpty()
            && telephone.text.toString().trim{it<=' '}.isNotEmpty()
        )
        {
            return true
        }
        return false
    }
}