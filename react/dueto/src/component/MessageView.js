import React, { Component } from 'react'
import {Button, TextField} from 'material-ui'
import {getMessages, sendMessage} from "../utils/fetchData.js"

class MessageView extends Component {
  constructor() {
    super()

    this.state = {
      newMessage: undefined,
      messages: []
    }
  }

  componentDidMount() {
    getMessages()
      .then(data => {
        this.setState({genres: data.GenreList})
      })
      .catch(error => {
        console.error(error)
      })
  }

  postMessage() {
    sendMessages(this.state.newMessage)
      .then(data => {})
      .catch(error => {
        console.error(error)
      })
  }

  textChange = (event) => { 
    this.setState({newMessage: event.target.value})
  }

  getMessageChips() {
    const chips = []

    if(this.state.messages != undefined) {
      for(let i = 0; i < this.state.messages.length; i++) {
        const message = this.state.messages[i]
        if(message.sender = this.state.id) {
          chips.push(
            <Chip>message.message</Chip> 
          )
        }
        else if(message.sender = this.state.id) {
          chips.push(
            <Chip style={{alignSelf: "right"}}>message.message</Chip> 
          )
        }
      }
    }

    return chips
  }

  render() {
    return (
      <div style={{display: "flex", alignItems: "center"}}>
        <Card>
          <CardContent>
            {this.getMessageChips()}
            <div style={{display: "flex", flexDirection: "row"}}>
              <TextField 
                label="Password" 
                value={this.state.newMessage} 
                onChange={this.textChange}
              />
              <Button/>
            </div>
          </CardContent>
        </Card> 
      </div>
    )
  }
}

export default MessageView 
