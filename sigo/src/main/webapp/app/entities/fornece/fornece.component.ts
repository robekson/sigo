import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFornece } from 'app/shared/model/fornece.model';
import { ForneceService } from './fornece.service';
import { ForneceDeleteDialogComponent } from './fornece-delete-dialog.component';

@Component({
  selector: 'jhi-fornece',
  templateUrl: './fornece.component.html',
})
export class ForneceComponent implements OnInit, OnDestroy {
  forneces?: IFornece[];
  eventSubscriber?: Subscription;

  constructor(protected forneceService: ForneceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.forneceService.query().subscribe((res: HttpResponse<IFornece[]>) => (this.forneces = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInForneces();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFornece): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInForneces(): void {
    this.eventSubscriber = this.eventManager.subscribe('forneceListModification', () => this.loadAll());
  }

  delete(fornece: IFornece): void {
    const modalRef = this.modalService.open(ForneceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fornece = fornece;
  }
}
