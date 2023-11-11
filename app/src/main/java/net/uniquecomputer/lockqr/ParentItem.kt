package net.uniquecomputer.lockqr

import net.uniquecomputer.lockqr.Model.ChildItem

data class ParentItem(
    val title: String,
    val mList : List<ChildItem>
)