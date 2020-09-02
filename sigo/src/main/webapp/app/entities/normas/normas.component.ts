import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INormas } from 'app/shared/model/normas.model';
import { NormasService } from './normas.service';
import { NormasDeleteDialogComponent } from './normas-delete-dialog.component';

@Component({
  selector: 'jhi-normas',
  templateUrl: './normas.component.html',
})
export class NormasComponent implements OnInit, OnDestroy {
  normas?: INormas[];
  eventSubscriber?: Subscription;

  constructor(protected normasService: NormasService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.normasService.query().subscribe((res: HttpResponse<INormas[]>) => (this.normas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNormas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INormas): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNormas(): void {
    this.eventSubscriber = this.eventManager.subscribe('normasListModification', () => this.loadAll());
  }

  delete(normas: INormas): void {
    const modalRef = this.modalService.open(NormasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.normas = normas;
  }
}
