/*
 *
 * to do sample project
 *
 */


const deleteButton = document.getElementById('category-delete-button')

deleteButton.onclick = function() {
  const res = window.confirm(
    "カテゴリを消すとそれに紐づく\nTODOも消えてしまいますがよろしいですか？"
  )

  if(!res) {
    alert("キャンセルしました")
    onsubmit="check(); return false;"
  }
}
