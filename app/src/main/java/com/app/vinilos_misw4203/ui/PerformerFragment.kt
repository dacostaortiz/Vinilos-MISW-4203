package com.app.vinilos_misw4203.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vinilos_misw4203.R
import com.app.vinilos_misw4203.databinding.PerformerFragmentBinding
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.ui.adapters.PerformerAdapter
import com.app.vinilos_misw4203.viewmodels.PerformerViewModel

class PerformerFragment : Fragment() {
    private var _binding: PerformerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PerformerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = PerformerAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.performersRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(activity.application))
            .get(PerformerViewModel::class.java)

        // Show progress bar while loading
        binding.progressBar.visibility = View.VISIBLE

        viewModel.performers.observe(viewLifecycleOwner) { performers ->
            performers?.apply {
                viewModelAdapter!!.performers = this
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) {
                onNetworkError()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}