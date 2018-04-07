import React, { Component } from 'react'
import Home from "./pages/Home.js"
import Login from "./pages/Login.js"
import Logout from "./pages/Logout.js"
import Profile from "./pages/Profile.js"
import Artist from "./pages/Artist.js"
import EditProfile from "./pages/EditProfile.js"
import AddVideo from "./pages/AddVideo.js"
import Discover from "./pages/Discover.js"
import Video from "./pages/Video.js"
import Genre from "./pages/Genre.js"
import CreateUser from "./pages/CreateUser.js"
import {BrowserRouter, Switch, Route} from "react-router-dom"

class Dueto extends Component {
  render() {
    return (
      <BrowserRouter>
        <div>
          <Switch>
            <Route path="/home" component={Home}/>
            <Route path="/profile" component={Profile}/>
            <Route path="/artist/:name" component={Artist}/>
            <Route path="/editprofile" component={EditProfile}/>
            <Route path="/addvideo" component={AddVideo}/>
            <Route path="/login" component={Login}/>
            <Route path="/logout" component={Logout}/>
            <Route path="/discover" component={Discover}/>
            <Route path="/video" component={Video}/>
            <Route name="genre" path="/genre/:name" component={Genre}/>
            <Route path="/createuser" component={CreateUser}/>
          </Switch>
        </div>
      </BrowserRouter>
    )
  }
}

export default Dueto;
