import React, { Component } from "react"
import { Card, Button, Typography } from "material-ui"
import { CardMedia, CardContent, CardActions } from "material-ui/Card"
import VideoDialog from "./VideoDialog.js"
import { Link } from "react-router-dom"
import { getThumbnailUrl } from "../utils/fetchData.js"

class VideoCard extends Component {
  constructor() {
    super()
  
    this.state = {
      open: false
    }
  }

  getVideoImage() {
    let image = getThumbnailUrl(this.props.id)
    
    return image
  }

  viewVideo = () => {
    this.setState({open: true})
  }

  closeDialog = () => {
    this.setState({open: false})
  }

  profile = () => {
    document.getElementById("profile").click()
  }

  render() {
    const profileUrl = "/artist/" + this.props.id    

    return (
      <Card style={{width: 400, margin: 40}}>
        <CardMedia>
          <img alt={this.props.name} src={this.getVideoImage()} style={{width: 400}}/>
        </CardMedia>
        <CardContent>
          <Typography variant="headline" component="h2">
            {this.props.name}
          </Typography>
          <Typography>
            {this.props.desc}
          </Typography>
        </CardContent>
        <CardActions>
          <Button onClick={this.viewVideo} size="small" color="primary">
            View  
          </Button>
          <Button onClick={this.profile} size="small" color="primary">
            Artist 
          </Button>
        </CardActions> 
        <VideoDialog 
          close={this.closeDialog}
          open={this.state.open}
          videoId={this.props.id}
          artist={this.props.artist}
          name={this.props.name}
        />
        <Link id="profile" to={profileUrl} style={{visibility: "collapsed"}}/>
      </Card>
    )
  }
}

export default VideoCard
