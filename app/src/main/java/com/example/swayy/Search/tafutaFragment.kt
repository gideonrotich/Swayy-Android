package com.example.swayy.Search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.example.swayy.R
import com.example.swayy.homeFragment
import kotlinx.android.synthetic.main.activity_electronics.*
import kotlinx.android.synthetic.main.activity_itemsale.*
import kotlinx.android.synthetic.main.fragment_kaba.view.*
import kotlinx.android.synthetic.main.fragment_tafuta.*
import kotlinx.android.synthetic.main.fragment_tafuta.view.*


class tafutaFragment : Fragment() {
    private lateinit var shad:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, SearchFragment()).commit()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tafuta, container, false)

        val pref = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if (pref != null) {
            this.shad = pref.getString("shad", "none").toString()
        }


        val spinnertwo = view.findViewById<Spinner>(R.id.vifaachuma)
        if (spinnertwo != null) {

            val electronics = resources.getStringArray(R.array.electronics)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnertwo.adapter = adapter
            spinnertwo.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnerwatoto = view.findViewById<Spinner>(R.id.watotow)
        if (spinnerwatoto != null) {

            val electronics = resources.getStringArray(R.array.babies)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerwatoto.adapter = adapter
            spinnerwatoto.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinneragri = view.findViewById<Spinner>(R.id.shambaw)
        if (spinneragri != null) {

            val electronics = resources.getStringArray(R.array.agriculture)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinneragri.adapter = adapter
            spinneragri.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnerwan = view.findViewById<Spinner>(R.id.wanyamaw)
        if (spinnerwan != null) {

            val electronics = resources.getStringArray(R.array.animals)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerwan.adapter = adapter
            spinnerwan.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnerchum = view.findViewById<Spinner>(R.id.chumaw)
        if (spinnerchum != null) {

            val electronics = resources.getStringArray(R.array.equipment)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerchum.adapter = adapter
            spinnerchum.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnerng = view.findViewById<Spinner>(R.id.nguow)
        if (spinnerng != null) {

            val electronics = resources.getStringArray(R.array.fashion)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerng.adapter = adapter
            spinnerng.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinneraf = view.findViewById<Spinner>(R.id.afyaw)
        if (spinneraf != null) {

            val electronics = resources.getStringArray(R.array.health)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinneraf.adapter = adapter
            spinneraf.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnermb = view.findViewById<Spinner>(R.id.mbaow)
        if (spinnermb != null) {

            val electronics = resources.getStringArray(R.array.furniture)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnermb.adapter = adapter
            spinnermb.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnerkz = view.findViewById<Spinner>(R.id.kaziw)
        if (spinnerkz != null) {

            val electronics = resources.getStringArray(R.array.jobs)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerkz.adapter = adapter
            spinnerkz.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnersm = view.findViewById<Spinner>(R.id.simuw)
        if (spinnersm != null) {

            val electronics = resources.getStringArray(R.array.mobile)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnersm.adapter = adapter
            spinnersm.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnerml = view.findViewById<Spinner>(R.id.maliw)
        if (spinnerml != null) {

            val electronics = resources.getStringArray(R.array.property)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerml.adapter = adapter
            spinnerml.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnerpr = view.findViewById<Spinner>(R.id.tengenezaw)
        if (spinnerpr != null) {

            val electronics = resources.getStringArray(R.array.repair)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnerpr.adapter = adapter
            spinnerpr.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnertf = view.findViewById<Spinner>(R.id.tafutaw)
        if (spinnertf != null) {

            val electronics = resources.getStringArray(R.array.seeking)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnertf.adapter = adapter
            spinnertf.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnervt = view.findViewById<Spinner>(R.id.vituw)
        if (spinnervt != null) {

            val electronics = resources.getStringArray(R.array.services)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnervt.adapter = adapter
            spinnervt.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }

        val spinnermc = view.findViewById<Spinner>(R.id.mchezow)
        if (spinnermc != null) {

            val electronics = resources.getStringArray(R.array.sports)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnermc.adapter = adapter
            spinnermc.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }
        val spinnergr = view.findViewById<Spinner>(R.id.gariw)
        if (spinnergr != null) {

            val electronics = resources.getStringArray(R.array.vehicles)
            val adapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_dropdown_item, electronics
                )
            }
            spinnergr.adapter = adapter
            spinnergr.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val me = electronics[position]
                    wapionevifaaa.text = me
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        }




        view.btnnextfashion_me.setOnClickListener {
            val pref = requireContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            pref.putString("kaba", view.wapionevifaaa.text.toString())
            pref.putString("shad", shad)
            pref.apply()
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, KabaFragment()).commit()
        }
        view.optomew.setOnClickListener {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, SearchFragment()).commit()
        }


        if (shad == "Electronics") {
            view.vifaachuma.visibility = View.VISIBLE
        }
        if (shad == "Babies and kids") {
            view.watotow.visibility = View.VISIBLE
        }
        if (shad == "Agriculture and food") {
            view.shambaw.visibility = View.VISIBLE
        }
        if (shad == "Animals and pets") {
            view.wanyamaw.visibility = View.VISIBLE
        }
        if (shad == "Commercial equipment and tools") {
            view.chumaw.visibility = View.VISIBLE
        }
        if (shad == "Fashion") {
            view.nguow.visibility = View.VISIBLE
        }
        if (shad == "Health and beauty") {
            view.afyaw.visibility = View.VISIBLE
        }
        if (shad == "Home furniture and appliances") {
            view.mbaow.visibility = View.VISIBLE
        }
        if (shad == "Jobs") {
            view.kaziw.visibility = View.VISIBLE
        }
        if (shad == "Mobile phones and tablets") {
            view.simuw.visibility = View.VISIBLE
        }
        if (shad == "Property") {
            view.maliw.visibility = View.VISIBLE
        }
        if (shad == "Repair and Construction") {
            view.tengenezaw.visibility = View.VISIBLE
        }
        if (shad == "Seeking work eg cv") {
            view.tafutaw.visibility = View.VISIBLE
        }
        if (shad == "Services") {
            view.vituw.visibility = View.VISIBLE
        }
        if (shad == "Sports arts and outdoors") {
            view.mchezow.visibility = View.VISIBLE
        }
        if (shad == "Vehicles") {
            view.gariw.visibility = View.VISIBLE
        }


        return view
    }


}