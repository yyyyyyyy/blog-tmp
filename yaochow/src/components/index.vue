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
        <form @submit.prevent="ListNoteNameByAccountId2ndCategory">
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
          <li @click="getcontent(index)" class="li-note" v-for="(note, index) in notes" :key="note.id">
            {{note.noteName}}
          </li>
          <li @click="add_note" class="li-add">
            click here to add note
          </li>
        </ol>
      </div>
      <div class="content4" v-if="content4_on" >
        <mavon-editor @save="$update" :subfield="false" v-model="current_note.noteContent"/>
      </div>
      <div class="content5" v-if="content5_on">
        <div class="content5-input">^^input topic here^^ <input type="text" v-model="new_note.noteName"/></div>
        <mavon-editor @save="$save" v-model="new_note.noteContent"/>
        <div @click="add_return" class="add-return">click here to return note list</div>
      </div>
    </div>
    <!--<div class="footer">
      <p>Copyright &copy; 2018.Company name All rights reserved.xxxxxxx <a href="http://yaochow.com/" target="_blank"
                                                                           title="xxxxxxx">xxxxxxx</a> - Collect from <a
        href="http://www.yaochow.com/" title="xxxxxxx" target="_blank">xxxxxxx</a></p>
    </div>-->
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'index',
  data () {
    return {
      content1_on: false,
      content2_on: false,
      content3_on: true,
      content4_on: false,
      content5_on: false,
      username: 'USERNAME',
      password: 'PASSWORD',
      email: 'EMAIL',
      current_note: {},
      new_note: {},
      preview: 'preview',
      notes: []
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
    getcontent (id) {
      this.current_note = this.notes[id]
      this.content4_on = true
    },
    submit () {
      const postData = {
        username: this.username,
        password: this.password
      }
      axios.post('http://www.yaochow.com/user/login', JSON.stringify(postData), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }

      }).then((response) => {
        if (response.data.success) {
          axios.get('http://www.yaochow.com/core/note/ListNoteNameByAccountId2ndCategory?category=', {
            headers: {
              withCredentials: true
            }
          }).then((response) => {
            if (response.data.success) {
              if (response.data.response) {
                this.notes = JSON.parse(response.data.response)
              }
              this.content1_on = false
              this.content2_on = false
              this.content3_on = true
            }
          }).catch((response) => {
            console.info(response)
            alert(response.data.errorMsg)
          })
        } else {
          alert(response.data.errorMsg)
        }
      }).catch((response) => {
        alert(response.data.errorMsg)
      })
    },
    list_notename_by_accountid2ndcategory () {
      axios.get('http://www.yaochow.com/core/note/ListNoteNameByAccountId2ndCategory?category=', {
        headers: {
          withCredentials: true
        }
      }).then(function (response) {
        console.info(response)
      }).catch(function (response) {
        console.info(response)
      })
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
    $save () {
      axios.post('http://www.yaochow.com/core/note/insert', JSON.stringify(this.new_note), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }

      }).then((response) => {
        if (response.data.success) {
          axios.get('http://www.yaochow.com/core/note/ListNoteNameByAccountId2ndCategory?category=', {
            headers: {
              withCredentials: true
            }
          }).then((response) => {
            if (response.data.success) {
              if (response.data.response) {
                this.notes = JSON.parse(response.data.response)
              }
              this.content5_on = false
              this.content3_on = true
            }
          }).catch((response) => {
            console.info(response)
            alert(response.data.errorMsg)
          })
        } else {
          alert(response.data.errorMsg)
        }
      }).catch((response) => {
        alert(response.data.errorMsg)
      })
    },
    $update () {
      axios.post('http://www.yaochow.com/core/note/updateById', JSON.stringify(this.current_note), {
        headers: {
          'Content-Type': 'application/json',
          withCredentials: true
        }

      }).then((response) => {
        if (response.data.success) {
          axios.get('http://www.yaochow.com/core/note/getNoteById/' + this.current_note.id, {
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
        } else {
          alert(response.data.errorMsg)
        }
      }).catch((response) => {
        alert(response.data.errorMsg)
      })
    }
  },
  mounted () {
    axios.get('http://www.yaochow.com/core/note/ListNoteNameByAccountId2ndCategory?category=', {
      headers: {
        withCredentials: true
      }
    }).then((response) => {
      if (response.data.success) {
        if (response.data.response) {
          this.notes = JSON.parse(response.data.response)
        }
      }
    }).catch((response) => {
      this.content1_on = true
      this.content3_on = false
    })
  }
}
</script>
