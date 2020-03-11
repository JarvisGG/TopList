package com.topList.android

import android.os.Bundle
import com.topList.theme.base.ui.ThemeActivity

class MainActivity : ThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val retValue = super.onCreateOptionsMenu(menu)
//        val navigationView = findViewById(R.id.nav_view)
//        // The NavigationView already has these same navigation items, so we only add
//        // navigation items to the menu here if there isn't a NavigationView
//        if (navigationView == null) {
//            getMenuInflater().inflate(R.menu.menu_overflow, menu)
//            return true
//        }
//        return retValue
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(
//            item, Navigation.findNavController(this, R.id.frag_nav_host)
//        ) || super.onOptionsItemSelected(item)
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(
//            Navigation.findNavController(this, R.id.frag_nav_host), mDrawerLayout
//        )
//    }
}

