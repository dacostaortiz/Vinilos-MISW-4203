package com.app.vinilos_misw4203.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.vinilos_misw4203.databinding.HomeFragmentBinding
import com.app.vinilos_misw4203.R

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Manejo de clics en las tarjetas
        binding.albumContainer.setOnClickListener {
            // Navegar al fragmento de album
            findNavController().navigate(R.id.action_homeFragment_to_albumFragment)
        }

        binding.performerContainer.setOnClickListener {
            // Navegar al fragmento de performer
            findNavController().navigate(R.id.action_homeFragment_to_performerFragment)
        }

        binding.collectionContainer.setOnClickListener {
            // Mostrar un Toast diciendo que la funcionalidad no está disponible
            Toast.makeText(requireContext(), "Esta funcionalidad aún no está lista", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}