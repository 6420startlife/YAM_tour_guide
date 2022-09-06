package com.ptithcm.thuan6420.yam.ui.component.tasks

import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.yam.data.worker.RefreshTokenWorker
import com.ptithcm.thuan6420.yam.databinding.ActivityTasksBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.ui.component.author.AuthorViewModel
import com.ptithcm.thuan6420.yam.util.Constants.REFRESH_TOKEN_WORKER
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class TasksActivity : BaseActivity(), ViewPagerInterface {
    private lateinit var binding: ActivityTasksBinding
    private lateinit var tasksViewAdapter: TasksViewAdapter
    private lateinit var tasksViewPresenter: TasksViewPresenter

    override fun initViewBinding() {
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setEvent() {
        tasksViewAdapter = TasksViewAdapter(supportFragmentManager, lifecycle)
        tasksViewPresenter = TasksViewPresenter(this)
        binding.vp2Task.adapter = tasksViewAdapter
        binding.bottomNavigationView.setOnItemSelectedListener {
            tasksViewPresenter.changeViewPager(it)
            true
        }
        binding.vp2Task.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tasksViewPresenter.changeViewPagerOnCallBack(position)
            }
        })
    }

    override fun switchPageSelected(position: Int) {
        binding.vp2Task.currentItem = position
    }

    override fun changePageOnCallback(id: Int) {
        binding.bottomNavigationView.menu.findItem(id).isChecked = true
    }
}