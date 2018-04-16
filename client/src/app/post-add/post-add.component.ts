import {Component} from '@angular/core';
import {BlogWebService} from '../shared';

@Component({
  selector: 'app-blog-web-list',
  templateUrl: 'post-add.component.html',
  styleUrls: ['post-add.component.css'],
  providers: [BlogWebService]
})

export class PostAddComponent {

  constructor(private blogWebService: BlogWebService) { }

  addPosts(title: string, content: string) {
    const posts: Posts = {
      id: null,
      title: title,
      content: content
    };
    this.blogWebService.post(posts).subscribe(data => {
    });
  }
}

export interface Posts {
  id: number;
  title: string;
  content: string;
}

