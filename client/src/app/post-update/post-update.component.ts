import {Component} from '@angular/core';
import {BlogWebService} from '../shared';
import {Posts} from "../post-add/post-add.component";

@Component({
  selector: 'app-blog-web-list',
  templateUrl: 'post-update.component.html',
  styleUrls: ['post-update.component.css'],
  providers: [BlogWebService]
})

export class PostUpdateComponent {

  private id: number;
  private title: string;
  private content: string;
  private showData: boolean = false;

  constructor(private blogWebService: BlogWebService) { }

  getPosts(postsId: number) {
    this.blogWebService.get(postsId).subscribe(data => {
      this.id = data.id;
      this.title = data.title;
      this.content = data.content;
      this.showData = true;
    })
  }

  updatePosts(title: string, content: string) {
    const posts: Posts = {
      id: this.id,
      title: title,
      content: content
    };
    this.blogWebService.update(posts).subscribe(data => {
    })
  }

}


