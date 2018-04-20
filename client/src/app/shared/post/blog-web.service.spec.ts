import { TestBed, inject } from '@angular/core/testing';

import { BlogWebService } from './blog-web.service';

describe('BlogWebService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BlogWebService]
    });
  });

  it('should be created', inject([BlogWebService], (service: BlogWebService) => {
    expect(service).toBeTruthy();
  }));
});
