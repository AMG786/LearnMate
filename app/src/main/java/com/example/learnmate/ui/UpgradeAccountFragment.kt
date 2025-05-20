package com.example.learnmate.ui

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnmate.MainActivity.Companion.LOAD_PAYMENT_DATA_REQUEST_CODE
import com.example.learnmate.R
import com.example.learnmate.databinding.FragmentHistoryBinding
import com.example.learnmate.databinding.FragmentUpgradeAccountBinding
import com.example.learnmate.ui.adapter.HistoryAdapter
import com.example.learnmate.ui.model.Question
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import org.json.JSONObject

val paymentDataRequestJson = JSONObject("""
{
  "apiVersion": 2,
  "apiVersionMinor": 0,
  "allowedPaymentMethods": [{
    "type": "CARD",
    "parameters": {
      "allowedAuthMethods": ["PAN_ONLY", "CRYPTOGRAM_3DS"],
      "allowedCardNetworks": ["MASTERCARD", "VISA"]
    },
    "tokenizationSpecification": {
      "type": "PAYMENT_GATEWAY",
      "parameters": {
        "gateway": "example",  // replace with real gateway like "stripe"
        "gatewayMerchantId": "exampleMerchantId"
      }
    }
  }],
  "transactionInfo": {
    "totalPriceStatus": "FINAL",
    "totalPrice": "10.00",
    "currencyCode": "USD"
  },
  "merchantInfo": {
    "merchantName": "Your App Name"
  }
}
""")


class UpgradeAccountFragment : Fragment() {

    private var _binding: FragmentUpgradeAccountBinding? = null
    private val binding get() = _binding!!
    lateinit var paymentsClient: PaymentsClient


    fun createPaymentsClient(context: Context): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST) // Change to PRODUCTION later
            .build()
        return Wallet.getPaymentsClient(context, walletOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpgradeAccountBinding.inflate(inflater, container, false)
        paymentsClient=createPaymentsClient(requireContext());
        binding.materialButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnPurchaseStarter.setOnClickListener {
            val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                context as Activity,
                LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}