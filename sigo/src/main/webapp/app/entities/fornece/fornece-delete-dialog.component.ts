import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFornece } from 'app/shared/model/fornece.model';
import { ForneceService } from './fornece.service';

@Component({
  templateUrl: './fornece-delete-dialog.component.html',
})
export class ForneceDeleteDialogComponent {
  fornece?: IFornece;

  constructor(protected forneceService: ForneceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.forneceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('forneceListModification');
      this.activeModal.close();
    });
  }
}
