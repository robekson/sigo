import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from './materia-prima.service';
import { MateriaPrimaDeleteDialogComponent } from './materia-prima-delete-dialog.component';

@Component({
  selector: 'jhi-materia-prima',
  templateUrl: './materia-prima.component.html',
})
export class MateriaPrimaComponent implements OnInit, OnDestroy {
  materiaPrimas?: IMateriaPrima[];
  eventSubscriber?: Subscription;

  constructor(
    protected materiaPrimaService: MateriaPrimaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.materiaPrimaService.query().subscribe((res: HttpResponse<IMateriaPrima[]>) => (this.materiaPrimas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMateriaPrimas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMateriaPrima): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMateriaPrimas(): void {
    this.eventSubscriber = this.eventManager.subscribe('materiaPrimaListModification', () => this.loadAll());
  }

  delete(materiaPrima: IMateriaPrima): void {
    const modalRef = this.modalService.open(MateriaPrimaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.materiaPrima = materiaPrima;
  }
}
