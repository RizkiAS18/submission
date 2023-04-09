package com.example.githubuserapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.data.model.UserResponse
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.detail.DetailActivity
import com.example.githubuserapp.ui.favorite.FavoriteActivity
import com.example.githubuserapp.ui.theme.SettingPreference
import com.example.githubuserapp.ui.theme.ThemeSettingActivity
import com.example.githubuserapp.ui.theme.ThemeSettingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvList: RecyclerView

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        viewModel.listUser.observe(this){gitUserList ->
            showRecyclerList(gitUserList)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.error.observe(this){
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            viewModel.doneToastError()
        }

        rvList = binding.rvProfileName
        rvList.setHasFixedSize(true)

        val setpref = SettingPreference.getInstance(dataStore)

        val themeSettingView = ViewModelProvider(this, ViewModelFactory(setpref))[ThemeSettingViewModel::class.java]

        themeSettingView.getThemeSetting().observe(this){isDarkModeActive: Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.rvLoading.visibility = View.VISIBLE
        }else{
            binding.rvLoading.visibility = View.GONE
        }

    }

    private fun showRecyclerList(gitUserList: List<UserResponse>) {
        rvList.layoutManager = LinearLayoutManager(this)
        val searchUserAdapter = MainAdapter(gitUserList)
        rvList.adapter = searchUserAdapter

        searchUserAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val intentDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentDetail.putExtra(EXTRA_DATA, data)
                startActivity(intentDetail)
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                viewModel.detailUser()
                return true
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query.toString())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.themeSetting -> {
                Intent(this@MainActivity, ThemeSettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}