import React, { Component } from 'react'
import {Button, TextField, Paper} from 'material-ui'
import Card, { CardContent } from "material-ui/Card"
import {getMessages, sendMessage} from "../utils/fetchData.js"

class MessageView extends Component {
  constructor() {
    super()

    this.state = {
      newMessage: undefined,
      messages: [],
      time: undefined
    }
  }

  tick = () => {
    this.setState({ time: Date.now() })
  }

  componentDidMount() {
    getMessages(this.props.artist)
      .then(data => {
        if(data !== null && data !== undefined) {
          this.setState({messages: data})
        }
      })
      .catch(error => {
        console.error(error)
      })

    this.interval = setInterval(() => this.tick(), 5000)
  }

  postMessage = () => {
    sendMessage(this.state.newMessage, this.props.artist)
      .then(data => {
        this.setState({newMessage: ""})
        getMessages(this.props.artist)
          .then(data => {
            if(data !== null && data !== undefined) {
              this.setState({messages: data})
          }
        })
        .catch(error => {
          console.error(error)
        })
      })
      .catch(error => {
        console.error(error)
      })
  }

  textChange = (event) => { 
    this.setState({newMessage: event.target.value})
  }

  getMessageChips() {
    const chips = []

    if(this.state.messages !== undefined && this.state.messages.length > 0) {
      for(let i = 0; i < this.state.messages.length; i++) {
        const message = this.state.messages[i]
        if(message.Artist === this.props.artist) {
          chips.push(
            <Paper style={{width: "fit-content", padding: 5, alignSelf: "flex-start", marginBottom: 10}}>{message.Message}</Paper> 
          )
        }
        else {
          chips.push(
            <Paper style={{width: "fit-content", padding: 5, alignSelf: "flex-end", marginBottom: 10}}>{message.Message}</Paper> 
          )
        }
      }
    }
    else {
      return (
        <label>No messages</label>
      )
    }

    return chips
  }

  render() {
    return (
      <div style={{height: "fit-content", display: "flex", justifyContent: "center", marginTop: 20}}>
        <Card>
          <CardContent>
            <div style={{margin: 10, display: "flex", flexDirection: "column"}}>
              {this.getMessageChips()}
            </div>
            <div style={{display: "flex", flexDirection: "row"}}>
              <TextField 
                value={this.state.newMessage} 
                onChange={this.textChange}
              />
              <Button onClick={this.postMessage}>Send</Button>
            </div>
          </CardContent>
        </Card> 
      </div>
    )
  }
}

export default MessageView 
