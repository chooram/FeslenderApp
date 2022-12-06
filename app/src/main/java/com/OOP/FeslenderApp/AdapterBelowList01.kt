package com.OOP.FeslenderApp

//import com.example.calfin.ListItems
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.OOP.FeslenderApp.databinding.ListItemBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AdapterBelowList01: RecyclerView.Adapter<AdapterBelowList01.ViewHolder>()  {


    private lateinit var items: ArrayList<FesData>

    fun build(i:ArrayList<FesData>): AdapterBelowList01{
        items = i
        return this
    }


    //img,name,loc,date
    class ViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(name: String, loc: String, date: String, imgName: String) {

            with(binding) {
                txtNameInList.text = name
                txtLocInList.text = loc
                txtWhenInList.text = date
                setImage(imgName)


                ////
                val result = name
                val bundle = bundleOf("clickItem" to result)
                rvProfileItem.setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.action_entryFragment_to_festivlaDetailFragment,bundle)
                )
                /*if(!imgName.isNullOrEmpty()) {
                    setImage(imgName)
                }

                 */
            }
        }



        fun setImage(imgName: String){
            val storageRef = FirebaseStorage.getInstance().reference.child("$imgName.png")

            val localfile = File.createTempFile("tempImage", "png")
            storageRef.getFile(localfile).addOnSuccessListener {

                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imageView.setImageBitmap(bitmap)
            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        var selectedDate = LocalDate.now()

        @RequiresApi(Build.VERSION_CODES.O)
        fun YearFromDate(date: LocalDate): String{
            val formatter = DateTimeFormatter.ofPattern("yyyy")

            return date.format(formatter)
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun MonthFromDate(date: LocalDate) : String{
            val formatter = DateTimeFormatter.ofPattern("MM")

            return  date.format(formatter)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(items[position].name, items[position].location, items[position].date, items[position].poster)
        //.bind03((color))

        val layoutParams = holder.itemView.layoutParams
        layoutParams.height =200
        holder.itemView.requestLayout()

    }
    override fun getItemCount(): Int = items.size

}