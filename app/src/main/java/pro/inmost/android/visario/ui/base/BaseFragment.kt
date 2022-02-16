package pro.inmost.android.visario.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import pro.inmost.android.visario.ui.activities.MainActivity


/**
 * Base generic [Fragment]
 *
 * @param T [ViewDataBinding] of fragment's layout
 * @property layId id of fragment's layout
 *
 */
abstract class BaseFragment<T : ViewDataBinding>(private val layId: Int) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    open var bottomNavViewVisibility = View.GONE

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavVisibility(bottomNavViewVisibility)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavVisibility(bottomNavViewVisibility)
        }
        onCreated()
    }

    /**
     * Called immediately after dialog created
     *
     */
    abstract fun onCreated()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}