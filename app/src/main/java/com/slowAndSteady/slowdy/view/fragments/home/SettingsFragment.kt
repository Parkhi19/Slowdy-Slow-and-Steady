package com.slowAndSteady.slowdy.view.fragments.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.slowAndSteady.slowdy.SharedPreference
import com.slowAndSteady.slowdy.databinding.FragmentSettingsBinding
import com.slowAndSteady.slowdy.view.receiver.AlarmReceiver


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        binding.settingsFragmentBackBtn.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
            navController.navigate(action)
        }
        binding.nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            SharedPreference.instance.nightModeEnabled = isChecked
            if (binding.nightModeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.nightModeText.text = "Night Mode On"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.nightModeText.text = "Night Mode Off"
            }
        }

        binding.sendMessageIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:parkhigoyal46@gmail.com")
            }
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            SharedPreference.instance.notificationEnabled = isChecked
            if (isChecked) {
                binding.notificationsText.text = "Notifications On"
            } else {
                binding.notificationsText.text = "Notifications Off"
                val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                val myIntent = Intent(requireContext(), AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(), 1, myIntent, PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager?.cancel(pendingIntent)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.notificationsSwitch.isChecked = SharedPreference.instance.notificationEnabled
        binding.nightModeSwitch.isChecked = SharedPreference.instance.nightModeEnabled
    }

}