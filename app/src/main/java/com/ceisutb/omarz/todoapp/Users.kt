package com.ceisutb.omarz.todoapp



class User{
        companion object Factory{
            fun create(): User = User()
        }
        var user: String? = null
        var pass: String? = null
}
