package com.berteodosio.samplelitica.congressman.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.berteodosio.samplelitica.R
import com.berteodosio.samplelitica.congressman.model.Congressman
import com.berteodosio.samplelitica.toolkit.extension.inflate
import com.berteodosio.samplelitica.toolkit.extension.loadImage
import java.util.*

class CongressmanListAdapter(private val mContext: Context) : RecyclerView.Adapter<CongressmanListAdapter.ViewHolder>() {

    private var mCongressmanList: MutableList<Congressman> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_congressman_list)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val congressman = mCongressmanList[position]
        populateItems(holder, congressman)
    }

    private fun populateItems(holder: ViewHolder, congressman: Congressman) {
        holder.mSocialName.text = congressman.socialName
        holder.mRealName.text = congressman.name
        holder.mParty.text = congressman.party
        holder.mProfilePicture.loadImage(congressman.photoUrl)
    }

    override fun getItemCount(): Int {
        return mCongressmanList.size
    }

    fun updateCongressmanList(congressmanList: MutableList<Congressman>) {
        mCongressmanList = congressmanList
        notifyDataSetChanged()
    }

    fun addCongressman(congressman: Congressman) {
        mCongressmanList.add(congressman)
        notifyItemChanged(mCongressmanList.size - 1)
    }

    class ViewHolder(mRoot: View) : RecyclerView.ViewHolder(mRoot) {
        internal val mProfilePicture: ImageView
        internal val mSocialName: TextView
        internal val mRealName: TextView
        internal val mParty: TextView

        init {
            mProfilePicture = mRoot.findViewById(R.id.congressman_list_picture) as ImageView
            mSocialName = mRoot.findViewById(R.id.congressman_list_social_name) as TextView
            mRealName = mRoot.findViewById(R.id.congressman_list_real_name) as TextView
            mParty = mRoot.findViewById(R.id.congressman_list_party) as TextView
        }
    }

}
