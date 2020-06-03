/**
 *
 * to do sample project
 *
 */
package model.site.todo

import play.api.data.Form
import play.api.mvc.Call
import lib.model.Category
import lib.model.Todo
import model.common.ViewValueCommon
import model.common.component._
import lib.model.Todo.FormValue

// Todoの編集、新規追加ページのviewvalue
case class ViewValueTodoForm(
  title:       String,
  allCategory: Seq[ViewValueCategory],
  formData:    Form[FormValue],
  postUrl:     Call,
  cssSrc:      Seq[String],
  jsSrc:       Seq[String]
) extends ViewValueCommon
