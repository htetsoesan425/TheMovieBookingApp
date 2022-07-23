import alirezat775.lib.carouselview.CarouselAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.padc.kotlin.ftc.themoviebooking.R
import com.padc.kotlin.ftc.themoviebooking.models.SampleModel
import kotlinx.android.synthetic.main.view_holder_carousel.view.*
import kotlinx.android.synthetic.main.view_holder_empty_carousel.view.*

/**
 * Author:  Alireza Tizfahm Fard
 * Date:    2019-06-14
 * Email:   alirezat775@gmail.com
 */

class CardCarouselAdapter : CarouselAdapter() {

    private val EMPTY_ITEM = 0
    private val NORMAL_ITEM = 1

    private var vh: CarouselViewHolder? = null
    var onClick: OnClick? = null

    fun setOnClickListener(onClick: OnClick?) {
        this.onClick = onClick
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItems()[position]) {
            is EmptySampleModel -> EMPTY_ITEM
            else -> NORMAL_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == NORMAL_ITEM) {
            val v = inflater.inflate(R.layout.view_holder_carousel, parent, false)
            vh = MyViewHolder(v)
            vh as MyViewHolder
        } else {
            val v = inflater.inflate(R.layout.view_holder_empty_carousel, parent, false)
            vh = EmptyMyViewHolder(v)
            vh as EmptyMyViewHolder
        }
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                vh = holder
                val model = getItems()[position] as SampleModel
                (vh as MyViewHolder).tvExpireDate.text = model.getId().toString()
            }
            else -> {
                vh = holder
                val model = getItems()[position] as EmptySampleModel
                (vh as EmptyMyViewHolder).titleEmpty.text = model.getText()
            }
        }
    }


    inner class MyViewHolder(itemView: View) : CarouselViewHolder(itemView) {

        var tvExpireDate: TextView = itemView.tvExpireDate

        init {
            tvExpireDate.setOnClickListener { onClick?.click(getItems()[adapterPosition] as SampleModel) }
        }

    }

    inner class EmptyMyViewHolder(itemView: View) : CarouselViewHolder(itemView) {
        var titleEmpty: TextView = itemView.item_empty_text
    }

    interface OnClick {
        fun click(model: SampleModel)
    }
}