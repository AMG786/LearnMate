package com.example.learnmate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.learnmate.R
import com.example.learnmate.ui.model.Task
//
//class TaskAdapter(
//    private val tasks: List<Task>,
//    private val onTaskClicked: (Task) -> Unit
//) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_task, parent, false)
//        return TaskViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        val task = tasks[position]
//        holder.bind(task)
//
//
//
//    }
//
//    override fun getItemCount(): Int = tasks.size
//
//    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val taskIcon: ImageView = itemView.findViewById(R.id.iv_task_icon)
//        private val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
//        private val taskDescription: TextView = itemView.findViewById(R.id.tv_task_description)
//        private val statusIndicator: View = itemView.findViewById(R.id.status_indicator)
//
//        fun bind(task: Task) {
//            taskTitle.text = task.title
//            taskDescription.text = task.description
//
//            val statusColor = when(task.status) {
//                Task.Status.COMPLETED -> R.color.green
//                Task.Status.IN_PROGRESS -> R.color.yellow
//                Task.Status.PENDING -> R.color.red
//            }
//            statusIndicator.setBackgroundResource(statusColor)
//
//            if (task.isImportant) {
//                taskIcon.setImageResource(R.drawable.ic_star)
//            } else {
//                taskIcon.setImageResource(R.drawable.ic_task)
//            }
//            // Set animation when item is clicked
//            itemView.setOnClickListener {
//                System.out.println("--------------Transition----------------------")
//                val motionLayout = itemView.findViewById<MotionLayout>(R.id.motion_layout)
//                motionLayout.transitionToEnd() // Start the animation
//                motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
//                    override fun onTransitionStarted(
//                        motionLayout: MotionLayout?,
//                        startId: Int,
//                        endId: Int
//                    ) {
//                        System.out.println("--------------Start Transition----------------------")
//                        // Optional: Handle animation start
//                    }
//
//                    override fun onTransitionChange(
//                        motionLayout: MotionLayout?,
//                        startId: Int,
//                        endId: Int,
//                        progress: Float
//                    ) {
//                        System.out.println("--------------Change Transition----------------------")
//                        // Optional: Handle animation progress
//                    }
//
//                    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
//                        // When animation completes, trigger the click action
//                        if (currentId == R.id.end) {
//                            onTaskClicked(task)
//                            motionLayout?.transitionToStart() // Reset animation
//                        }
//                        System.out.println("--------------Completed Transition----------------------")
//                    }
//
//                    override fun onTransitionTrigger(
//                        motionLayout: MotionLayout?,
//                        triggerId: Int,
//                        positive: Boolean,
//                        progress: Float
//                    ) {
//                        // Optional: Handle trigger events
//                        System.out.println("--------------Transition Trigger----------------------")
//                    }
//                })
//            }
//
//        }
//    }
//}

class TaskAdapter(
    private val tasks: List<Task>,
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskIcon: ImageView = itemView.findViewById(R.id.iv_task_icon)
        private val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        private val taskDescription: TextView = itemView.findViewById(R.id.tv_task_description)
        private val statusIndicator: View = itemView.findViewById(R.id.status_indicator)

        fun bind(task: Task) {
            taskTitle.text = task.title
            taskDescription.text = task.description

            val statusColor = when(task.status) {
                Task.Status.COMPLETED -> R.color.green
                Task.Status.IN_PROGRESS -> R.color.yellow
                Task.Status.PENDING -> R.color.red
            }
            statusIndicator.setBackgroundResource(statusColor)

            if (task.isImportant) {
                taskIcon.setImageResource(R.drawable.ic_star)
            } else {
                taskIcon.setImageResource(R.drawable.ic_task)
            }

            itemView.setOnClickListener {
                onTaskClicked(task)
            }
        }
    }
}