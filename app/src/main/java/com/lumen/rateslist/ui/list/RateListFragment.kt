package com.lumen.rateslist.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lumen.rateslist.EventObserver
import com.lumen.rateslist.R
import com.lumen.rateslist.databinding.RateListFragmentBinding
import com.lumen.rateslist.ui.list.item.RateItem

class RateListFragment : Fragment(), RateListAdapter.OnRateItemClickListener {

    private var _binding: RateListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RateListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RateListFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RateListAdapter(this)
        binding.ratesRecycler.layoutManager = LinearLayoutManager(context)
        binding.ratesRecycler.adapter = adapter

        viewModel.rateData.observe(viewLifecycleOwner) { rateDate ->
            adapter.submitList(rateDate)
        }

        binding.ratesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.load()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.scrollToTopBtn.isVisible = recyclerView.computeVerticalScrollOffset() > 0
            }
        })

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            binding.swipeToRefresh.isRefreshing = isRefreshing
        }

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.reloadData()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, EventObserver { errorMessage ->
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(R.string.dialog_error_title)

            if (errorMessage.isEmpty()) {
                alertDialogBuilder.setMessage(R.string.unknown_error)
            } else {
                alertDialogBuilder.setMessage(errorMessage)
            }

            alertDialogBuilder.setPositiveButton(R.string.dialog_error_close_btn) { dialog, which ->
                dialog.dismiss()
            }
            alertDialogBuilder.show()
        })

        binding.scrollToTopBtn.setOnClickListener {
            binding.ratesRecycler.smoothScrollToPosition(0)
        }
    }

    override fun onClick(rateItem: RateItem) {
        val action =
            RateListFragmentDirections.actionRateListFragmentToRateDetailFragment(
                rateItem.name,
                rateItem.value,
                rateItem.date,
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
