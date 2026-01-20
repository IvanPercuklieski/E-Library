import { TestBed } from '@angular/core/testing';

import { AdminPanel } from './admin-panel';

describe('AdminPanel', () => {
  let service: AdminPanel;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminPanel);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
