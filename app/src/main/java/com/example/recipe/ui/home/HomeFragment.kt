package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.recipe.databinding.FragmentHomeBinding
import com.mistershorr.birthdaytracker.Person

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onStart() {
        super.onStart()
        loadDataFromBackendless()
    }


    private fun loadDataFromBackendless() {
        val objectId = Backendless.UserService.CurrentUser().objectId
        val whereClause = "ownerId = '${objectId}'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        var callback = object :
            AsyncCallback<List<Person>?> {override fun handleResponse(foundPeople: List<Person>?) {
            Log.d("BirthdayList", "handleResponse: ${foundPeople}")
            val adapter = BirthdayAdapter((foundPeople?: listOf()))
            binding.recyclerViewBirthdayListPeople.adapter = adapter
            binding.recyclerViewBirthdayListPeople.layoutManager =
                LinearLayoutManager(this@MainActivity)
        }

            override fun handleFault(fault: BackendlessFault) {
                Log.d("BirthdayList", "handleFault: ${fault.message}")
            }
        }
        Backendless.Data.of(Person::class.java).find(queryBuilder, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}