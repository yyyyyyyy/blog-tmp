<template>
  <div class="main">
    <div class="log">
      <div class="content1" v-if="content1_on">
        <h2>Sign In</h2>
        <form @submit.prevent="submit">
          <input type="text" name="username" v-model="username" @click="username_in(username)" @blur="username_out">
          <input type="password" name="password" v-model="password" @click="password_in(password)" @blur="password_out">
          <div class="button-row">
            <input type="submit" class="sign-in" value="Sign In">
            <input type="button" class="login-register" value="Register" v-on:click="login_register">
            <div class="clear"></div>
          </div>
        </form>
      </div>
      <div class="content2" v-if="content2_on">
        <h2>Sign Up</h2>
        <form @submit.prevent="register">
          <input type="text" name="username" v-model="username" @click="username_in(username)" @blur="username_out">
          <input type="email" name="email" v-model="email" @click="email_in(email)" @blur="email_out">
          <input type="password" name="password" v-model="password" @click="password_in(password)" @blur="password_out">
          <input type="submit" class="register" value="Register">
          <input type="button" class="register-login" value="Sign In" v-on:click="register_login">
        </form>
        <div class="clear"></div>
      </div>
      <div class="content3" v-if="content3_on">
        <ol>
          <li class="li-bin" @click="bin_return" v-if="list_return_on">
            Return NoteList
          </li>
          <li class="li-bin" @click="recycle_bin" v-if="bin_enter_on">
            Recycle Bin
          </li>
          <li @click="get_content(index)" class="li-note" v-for="(note, index) in notes" :key="note.id" v-if="bin_enter_on">
            {{note.noteName}}
          </li>
          <li @click="get_bin_content(index)" class="li-note" v-for="(note, index) in bin_notes" :key="note.id" v-if="list_return_on">
            {{note.noteName}}
          </li>
          <li @click="add_note" class="li-add">
            Click Here 2 Add Note
          </li>
        </ol>
      </div>
      <div class="content4" v-if="content4_on" >
        <mavon-editor @save="update" :subfield="false" v-model="current_note.noteContent"/>
      </div>
      <div class="content5" v-if="content5_on">
        <div class="content5-input">*Input Topic Here >>> <input type="text" v-model="new_note.noteName"/></div>
        <mavon-editor @save="save" v-model="new_note.noteContent"/>
        <div @click="add_return" class="add-return">Click Here 2 Return NoteList</div>
      </div>
    </div>
    <!--<div class="footer">
      <p>Copyright &copy; 2018.Company name All rights reserved.xxxxxxx <a href="http://yaochow.com/" target="_blank"
                                                                           title="xxxxxxx">xxxxxxx</a> - Collect from <a
        href="http://localhost:8762/" title="xxxxxxx" target="_blank">xxxxxxx</a></p>
    </div>-->
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'index',
  data () {
    return {
      content1_on: false, // 登陆模块 -默认隐藏
      content2_on: false, // 注册模块 -默认隐藏
      content3_on: false, // 笔记列表模块 -默认隐藏
      content4_on: false, // 笔记内容模块 -默认隐藏
      content5_on: false, // 新增笔记模块 -默认隐藏
      list_return_on: false,
      bin_enter_on: true,
      username: 'USERNAME',
      password: 'PASSWORD',
      email: 'EMAIL',
      current_note: {}, // 当前显示的笔记
      new_note: {}, // 新增的笔记
      notes: [], // 缓存的笔记列表
      bin_notes: [],
      page_size: 8,
      page_number: 1
    }
  },
  methods: {
    username_in (username) {
      if (username === 'USERNAME') {
        this.username = ''
      }
    },
    password_in (password) {
      if (password === 'PASSWORD') {
        this.password = ''
      }
    },
    email_in (email) {
      if (email === 'EMAIL') {
        this.email = ''
      }
    },
    login_register () {
      this.username = 'USERNAME'
      this.password = 'PASSWORD'
      this.content1_on = false
      this.content2_on = true
    },
    register_login () {
      this.username = 'USERNAME'
      this.password = 'PASSWORD'
      this.content1_on = true
      this.content2_on = false
    },
    username_out () {
      if (this.username === '') {
        this.username = 'USERNAME'
      }
    },
    password_out () {
      if (this.password === '') {
        this.password = 'PASSWORD'
      }
    },
    email_out () {
      if (this.email === '') {
        this.email = 'EMAIL'
      }
    },
    get_content (id) {
      this.current_note = this.notes[id]
      this.content4_on = true
    },
    get_bin_content (id) {
      this.current_note = this.bin_notes[id]
      this.content4_on = false
    },
    add_note () {
      this.content3_on = false
      this.content4_on = false
      this.content5_on = true
    },
    add_return () {
      this.content5_on = false
      this.content3_on = true
      if (this.current_note.noteContent) {
        this.content4_on = true
      }
      this.new_note = {}
    },
    bin_return () {
      this.bin_enter_on = true
      this.list_return_on = false
    },
    submit () {
      const postData = {
        username: this.username,
        password: this.password
      }
      axios.post('http://localhost:8762/user/login', JSON.stringify(postData), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }
      }).then((response) => {
        if (response.data.success) {
          this.list_note(1)
        } else {
          alert(response.data)
        }
      }).catch((response) => {
        alert(response.data)
      })
    },
    register () {
      const postData = {
        username: this.username,
        password: this.password,
        email: this.email
      }
      axios.post('http://localhost:8762/user/register', JSON.stringify(postData), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }
      }).then((response) => {
        if (response.data.success) {
          this.content2_on = false
          this.content3_on = true
        } else {
          alert(response.data)
        }
      }).catch((response) => {
        alert(response.data)
      })
    },
    list_note (pageNumber) {
      const postData = {
        pageSize: this.pageSize,
        pageNumber: pageNumber
      }
      axios.post('http://localhost:8762/core/note/listNote', JSON.stringify(postData), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }
      }).then((response) => {
        if (response.data.success) {
          if (response.data.response) {
            this.notes = JSON.parse(response.data.response).content
            this.content1_on = false
            this.content2_on = false
            this.content3_on = true
            this.content5_on = false
          }
        } else {
          if (response.data.errorCode === '1') {
            this.content1_on = true
            this.content2_on = false
            this.content3_on = false
            this.content4_on = false
            this.content5_on = false
          }
        }
      }).catch((response) => {
        console.info(response)
      })
    },
    save () {
      axios.post('http://localhost:8762/core/note/insert', JSON.stringify(this.new_note), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }

      }).then((response) => {
        if (response.data.success) {
          this.new_note = {}
          this.list_note(this.page_number)
        } else {
          alert(response.data.errorMsg)
        }
      }).catch((response) => {
        alert(response.data.errorMsg)
      })
    },
    update () {
      axios.post('http://localhost:8762/core/note/updateById', JSON.stringify(this.current_note), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }
      }).then((response) => {
        if (response.data.success) {
          this.get_note_by_id(this.current_note.id)
        } else {
          alert(response.data.errorMsg)
        }
      }).catch((response) => {
        alert(response.data.errorMsg)
      })
    },
    get_note_by_id (id) {
      axios.get('http://localhost:8762/core/note/getNoteById/' + id, {
        headers: {
          withCredentials: true
        }
      }).then((response) => {
        if (response.data.success) {
          if (response.data.response) {
            alert('update successfully')
            this.current_note = JSON.parse(response.data.response)
          }
        }
      }).catch((response) => {
        console.info(response)
        alert(response.data.errorMsg)
      })
    },
    recycle_bin () {
      if (this.bin_notes.length === 0) {
        axios.get('http://localhost:8762/core/note/listDeletedNoteNameByAccountId', {
          headers: {
            withCredentials: true
          }
        }).then((response) => {
          if (response.data.success) {
            if (response.data.response) {
              this.bin_notes = JSON.parse(response.data.response)
            }
            this.bin_enter_on = false
            this.list_return_on = true
          }
        }).catch((response) => {
          console.info(response)
          alert(response.data.errorMsg)
        })
      }
    }
  },
  mounted () {
    this.list_note(1)
  }
}
</script>
