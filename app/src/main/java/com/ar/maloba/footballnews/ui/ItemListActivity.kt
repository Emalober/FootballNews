package com.ar.maloba.footballnews.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ar.maloba.footballnews.R

import com.ar.maloba.footballnews.model.Item
import com.ar.maloba.footballnews.model.RssResponse
import com.ar.maloba.footballnews.remote.RssAPI
import com.ar.maloba.footballnews.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var model: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        model = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
        getNews()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        // Create the observer which updates the UI.
        val listObserver = Observer<List<Item>> { news ->
            // Update the UI, in this case, a TextView.
            recyclerView.adapter = news?.let {
                SimpleItemRecyclerViewAdapter(
                    this,
                    it,
                    twoPane
                )
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.allNewsMutableLiveData.observe(this, listObserver)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<Item>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Item
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_ID, item.hashCode())
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.hashCode())
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.title
            holder.contentView.text = item.description
            holder.dateView.text = item.pubDate
            //holder.imageView.setImageURI( item.enclosure.url.)

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.title
            val contentView: TextView = view.content
            val dateView: TextView = view.date
            val imageView: ImageView = view.imageView
        }
    }


    private val API_BASE_URL = "https://www.telegraph.co.uk/football/"
    private var kk: List<Item>? = null

    fun getNews() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val api = retrofit.create<RssAPI>(RssAPI::class.java!!)

        val call = api.getNews()
        call.enqueue(object : Callback<RssResponse> {
            override fun onResponse(call: Call<RssResponse>, response: Response<RssResponse>) {
                kk = response.body()?.channel?.itemList
                model.allNewsMutableLiveData.value = kk
            }

            override fun onFailure(call: Call<RssResponse>, t: Throwable) {
                println(t.localizedMessage)
            }
        })
    }
}
