package com.padc.kotlin.ftc.themoviebooking.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.kotlin.ftc.themoviebooking.adapters.MovieAdapter
import com.padc.kotlin.ftc.themoviebooking.delegates.LoginDelegate
import com.padc.kotlin.ftc.themoviebooking.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    lateinit var mDelegate: MovieViewHolderDelegate
    lateinit var mMovieAdapter: MovieAdapter

    fun setUpMovieListViewPod(delegate: MovieViewHolderDelegate, title: String) {
        setDelegate(delegate)
        setUpRecyclerView()
        //lblMovieType.text = title
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieAdapter(mDelegate)
        rvMovieList.adapter = mMovieAdapter
        rvMovieList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


    private fun setDelegate(delegate: MovieViewHolderDelegate) {
        this.mDelegate = delegate
    }


}