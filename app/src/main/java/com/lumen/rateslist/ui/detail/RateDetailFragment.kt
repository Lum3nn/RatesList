package com.lumen.rateslist.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.lumen.rateslist.databinding.RateDetailFragmentBinding
import com.lumen.rateslist.repository.EmojiRepository

class RateDetailFragment : Fragment() {

    private var _binding: RateDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: RateDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RateDetailFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rateName.text = args.rateName
        binding.rateValue.text = args.rateValue.toString()
        binding.rateDate.text = args.rateDate
        binding.rateEmoji.text = EmojiRepository.getEmojiCode(args.rateName)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
