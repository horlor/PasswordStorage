package com.gmail.horloraa.passwordstore.repository.data

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object PasswordRecords : IntIdTable(){
    val webPage: Column<String> = varchar("webpage",250).default("")
    val username: Column<String> = varchar("username",100).index()
    val passwordIV = binary("passwordIv",16)
    val password = binary("password",255)
    val email: Column<String> = varchar("email",250).default("")
    val comment: Column<String> = varchar("comment",1000).default("")
}

class DbPasswordRecord(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<DbPasswordRecord>(PasswordRecords)
    var webPage by PasswordRecords.webPage
    var username by PasswordRecords.username
    var password by PasswordRecords.password
    var passwordIv by PasswordRecords.passwordIV
    var email by PasswordRecords.email
    var comment by PasswordRecords.comment
}