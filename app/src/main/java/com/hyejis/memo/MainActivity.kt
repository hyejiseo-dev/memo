package com.hyejis.memo

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

//DB에서 저장하거나 소모가 많은 작업은 onStop()에서 해야함!!
class MainActivity : AppCompatActivity(), OnDeleteListener{

    lateinit var db : MemoDatabase     // lateinit는 나중에 초기화 하기 위해 선언
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        btn_add.setOnClickListener {
            var memo = MemoEntity(null,edittext.text.toString())
            edittext.setText("")
            insertMemo(memo)
        }

        recyclerview.layoutManager = LinearLayoutManager(this)

        //처음에 다 나오게!
        getAllMemos()
    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data
    //4. Set RecyclerView

    fun insertMemo(memo : MemoEntity){
        val insertTask = object: AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().insert(memo)
            }

            override fun onPostExecute(result:Unit) {
                super.onPostExecute(result)
                getAllMemos()   //저장 후 다 가져오기
            }
        }

        insertTask.execute()
    }

    fun getAllMemos(){
        val getTask = object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result:Unit) {
                super.onPostExecute(result)
                setRecyclerView(memoList)  //저장한 메모를 리사이클뷰에 넣음
            }
        }

        getTask.execute()
    }

    fun deleteMemo(memo:MemoEntity){
        val deleteTask = object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result:Unit) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        deleteTask.execute()

    }

    fun setRecyclerView(memoList : List<MemoEntity>){
        recyclerview.adapter = MyAdapter(this,memoList,this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}