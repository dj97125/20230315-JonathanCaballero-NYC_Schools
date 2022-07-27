package com.example.nyc_schools_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nyc_schools_test.R
import com.example.nyc_schools_test.common.BaseFragment
import com.example.nyc_schools_test.common.InternetCheck
import com.example.nyc_schools_test.common.OnSchoolClicked
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.databinding.FragmentListSchoolBinding
import com.example.nyc_schools_test.domain.SchoolDomain


class FragmentListSchool : BaseFragment(), OnSchoolClicked {

    private val binding by lazy {
        FragmentListSchoolBinding.inflate(layoutInflater)
    }
    private val nycdapter by lazy {
        NYCAdapter(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.schoolsRecycler.apply {
            adapter = nycdapter
        }

        nycViewModel.schoolResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is StateAction.SUCCESS<*> -> {
                    val retrievedSchools = state.response as List<SchoolDomain>
                    binding.schoolsRecycler.visibility = View.VISIBLE
                    binding.swipeRefresh.visibility = View.VISIBLE

                    nycdapter.updateData(retrievedSchools)
                }
                is StateAction.ERROR -> {
                    binding.schoolsRecycler.visibility = View.GONE
                    binding.swipeRefresh.visibility = View.GONE

                    displayErrors(state.error.localizedMessage) {
                        nycViewModel.getSchoolList()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()


        binding.swipeRefresh.apply {
            setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,
                android.R.color.holo_green_dark,
            )
            binding.swipeRefresh.setOnRefreshListener {

                nycViewModel.getSchoolList()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun schoolClicked(school: SchoolDomain) {
        nycViewModel.school = school
        findNavController().navigate(R.id.action_fragmentListSchool_to_fragmentDetailsSchool)

    }
}