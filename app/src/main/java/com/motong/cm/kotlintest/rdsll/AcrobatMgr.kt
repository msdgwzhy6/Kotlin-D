package com.motong.cm.kotlintest.rdsll

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */
class AcrobatMgr<D> {

    internal val items: ArrayList<AcrobatItem<D>> by lazy {
        ArrayList<AcrobatItem<D>>()
    }

    internal val data by lazy {
        ArrayList<D?>()
    }

    fun item(create: () -> AcroLayoutItem<D>) {
        items.add(create())
    }

    fun itemDSL(create: AcrobatDSL<D>.() -> Unit) {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.create()
        items.add(acrobatDSL.build())
    }

    fun setData(list: List<D?>) {
        data.clear()
        data.addAll(list)
    }

    fun getItemConfig(position: Int): Int {
        if (items.isEmpty()) {
            throw RuntimeException("item must config")
        }
        items.forEachIndexed { index, acrobatItem ->
            if (acrobatItem.isMeetData(data[position], position)) {
                return index
            }
        }
        throw RuntimeException("can't find matched item")
    }
}