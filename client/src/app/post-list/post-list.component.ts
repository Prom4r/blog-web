import {Component, OnInit} from '@angular/core';
import {BlogWebService} from '../shared';
import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-blog-web-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css'],
  providers: [BlogWebService]
})
export class PostListComponent implements OnInit {
  postsList: Array<any>;
  displayedColumns = ['id', 'title', 'content', 'delete'];

  constructor(private blogWebService: BlogWebService) { }

  dataSource = new MatTableDataSource(this.postsList);

  ngOnInit() {
    this.blogWebService.getAll().subscribe(
      data => {
        this.dataSource.data = data;
      },
      error => console.log(error)
    )
  }

  onDelete(idPosts: number) {
    console.log(idPosts);
    this.blogWebService.delete(idPosts).subscribe(data => {
        this.blogWebService.getAll().subscribe(data => {
          this.dataSource.data = data;
        })
      },
      error => console.log(error)
    )
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }
}



