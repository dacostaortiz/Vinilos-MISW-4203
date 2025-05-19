package com.app.vinilos_misw4203.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.vinilos_misw4203.R
import com.app.vinilos_misw4203.databinding.PerformerDetailFragmentBinding
import com.app.vinilos_misw4203.models.Performer
import com.app.vinilos_misw4203.viewmodels.PerformerDetailViewModel
import com.bumptech.glide.Glide

class PerformerDetailFragment : Fragment() {
    private var _binding: PerformerDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerformerDetailViewModel
    private var performerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            performerId = PerformerDetailFragmentArgs.fromBundle(it).performerId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PerformerDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(
            this,
            PerformerDetailViewModel.Factory(activity.application)
        ).get(PerformerDetailViewModel::class.java)

        showLoading(true)

        viewModel.performer.observe(viewLifecycleOwner, Observer<Performer> { p ->
            p?.let {
                updateUI(it)
                showLoading(false)
            }
        })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isError ->
            if (isError) onNetworkError()
        })

        viewModel.refreshPerformerDetail(performerId)
    }

    private fun updateUI(p: Performer) {
        binding.performerName.text        = p.name
        binding.performerType.text        = p.performerType
        binding.performerBirthDate.text   = p.birthDate ?: ""
        binding.performerDescription.text = p.description

        Glide.with(this)
            .load(p.image)
            .placeholder(R.drawable.performer)
            .into(binding.performerImage)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = p.name
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility             =
            if (isLoading) View.VISIBLE else View.GONE
        binding.performerDetailFragment.visibility =
            if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
