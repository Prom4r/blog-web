import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {HttpHeaders} from '@angular/common/http'
import {Posts} from "../../post-add/post-add.component";

@Injectable()
export class BlogWebService {

  private static API_BASE_URI = 'http://localhost:8081/post/';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(BlogWebService.API_BASE_URI);
  }

  get(postsId: number): Observable<any> {
    return this.http.get(BlogWebService.API_BASE_URI + postsId, httpOptions);
  }

  post(posts: Posts): Observable<any> {
    return this.http.post(BlogWebService.API_BASE_URI, posts, httpOptions);
  }

  update(posts: Posts): Observable<any> {
    return this.http.put(BlogWebService.API_BASE_URI, posts, httpOptions);
  }

  delete(postsId: number): Observable<any> {
    return this.http.delete(BlogWebService.API_BASE_URI + postsId, httpOptions);
  }
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'my-auth-token'
  })
};
