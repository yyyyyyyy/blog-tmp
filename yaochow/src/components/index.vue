<template>
  <div class="main">
    <div id="log" class="log">
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
      </div>
      <div class="content3" v-if="content3_on">
        <ol>
          <li class="li-note" v-for="(note, index) in notes" :key="note.id">
            <a @click="getcontent(index)">{{note.noteName}}</a>
          </li>
        </ol>
      </div>
      <div id="main" class="content4" v-if="content4_on" >
        <mavon-editor :subfield="false" :default_open="preview" :editable="false" v-model="content"/>
      </div>
      <div class="clear"></div>
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
      username: 'USERNAME',
      password: 'PASSWORD',
      email: 'EMAIL',
      content: '',
      content3_class: 'content3',
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
      this.content = this.notes[id].noteContent
      this.content4_on = true
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
        console.info(response)
        if (response.data.success) {
          axios.get('http://localhost:8762/core/note/ListNoteNameByAccountId2ndCategory?category=', {
            headers: {
              withCredentials: true
            }
          }).then((response) => {
            console.info(response)
            if (response.data.success) {
              this.notes = JSON.parse(response.data.response)
              this.content1_on = false
              this.content2_on = false
              this.content3_on = true
            }
          }).catch((response) => {
            console.info(response)
          })
        }
      }).catch((response) => {
        console.info(response)
      })
    },
    ListNoteNameByAccountId2ndCategory () {
      axios.get('http://localhost:8762/core/note/ListNoteNameByAccountId2ndCategory?category=', {
        headers: {
          withCredentials: true
        }
      }).then(function (response) {
        console.info(response)
      }).catch(function (response) {
        console.info(response)
      })
    }
  },
  mounted () {
    axios.get('http://localhost:8762/core/note/ListNoteNameByAccountId2ndCategory?category=', {
      headers: {
        withCredentials: true
      }
    }).then((response) => {
      console.info(response)
      if (response.data.success) {
        this.notes = JSON.parse(response.data.response)
      }
    }).catch((response) => {
      console.info(response)
      this.content1_on = true
      this.content3_on = false
    })
  }
}
</script>
