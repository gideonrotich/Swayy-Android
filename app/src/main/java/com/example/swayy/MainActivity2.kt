package com.example.swayy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.google.firebase.messaging.FirebaseMessaging
import ke.co.mpesaapi.MpesaListener
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity(),MpesaListener {

    lateinit var daraja: Daraja

    companion object {
        lateinit var mpesaListener: MpesaListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        mpesaListener = this

        daraja = Daraja.with(
            "USmmBa0NqdmvJCG40ifZ6Uv5htGriGGw",
            "7br80HAJHFxaePV5",
            Env.PRODUCTION, //Remember to change this to Env.Production when using production credentials, Sandbox is just for test
            object : DarajaListener<AccessToken> {
                override fun onResult(accessToken: AccessToken) {

                    Toast.makeText(
                        this@MainActivity2,
                        "MPESA TOKEN : ${accessToken.access_token}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: String) {
                    Log.d("Token", "Token error $error")
                }
            })

        button.setOnClickListener {
            val phoneNumber = phone.text.trim().toString().trim()
            val lnmExpress = LNMExpress(
                "5672949", //Test credential but shortcode is mostly paybill number, email mpesa businnes fo clarification
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                TransactionType.CustomerPayBillOnline,  // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                "1",
                phoneNumber,
                "254112636599",
                phoneNumber,
                "https://us-central1-mpesaapisecond.cloudfunctions.net/myCallbackUrl", // call back url send back payload info if the transactions went through. Its important inorder to update ui after user has paid, its essential but the service can work without it.
                "001ABC",
                "Goods Payment"
            )

            daraja.requestMPESAExpress(lnmExpress,
                object : DarajaListener<LNMResult> {
                    override fun onResult(lnmResult: LNMResult) {

                        FirebaseMessaging.getInstance()
                            .subscribeToTopic(lnmResult.CheckoutRequestID.toString())
                        Toast.makeText(
                            this@MainActivity2,
                            lnmResult.ResponseDescription,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(error: String) {
                        Toast.makeText(
                            this@MainActivity2,
                            error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    override fun sendSuccessful(amount: String, phone: String, date: String, receipt: String) {


        runOnUiThread {
            Toast.makeText(
                this, "Payment Successful\n" +
                        "Receipt: $receipt\n" +
                        "Date: $date\n" +
                        "Phone: $phone\n" +
                        "Amount: $amount", Toast.LENGTH_LONG
            ).show()

        }
    }

    override fun sendFailed(reason: String) {

        runOnUiThread {
            Toast.makeText(
                this, "Payment Failed\n" +
                        "Reason: $reason", Toast.LENGTH_LONG
            ).show()
        }

    }
}
