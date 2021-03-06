/*
 *  Copyright (C) 2016 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may obtain
 *  a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.fortysevendeg.architecture.ui.main.adapters

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, ViewGroup}
import com.fortysevendeg.architecture.R
import com.fortysevendeg.architecture.services.api.Animal
import com.fortysevendeg.architecture.ui.main.holders.AnimalViewHolder
import macroid._

case class AnimalsAdapter(animals: Seq[Animal])(implicit context: ContextWrapper)
    extends RecyclerView.Adapter[AnimalViewHolder] {

  override def onCreateViewHolder(parent: ViewGroup, i: Int): AnimalViewHolder = {
    val v = LayoutInflater.from(parent.getContext).inflate(R.layout.image_item, parent, false)
    new AnimalViewHolder(v)
  }

  override def getItemCount: Int = animals.size

  override def onBindViewHolder(viewHolder: AnimalViewHolder, position: Int): Unit =
    viewHolder.bind(animals(position))

}

