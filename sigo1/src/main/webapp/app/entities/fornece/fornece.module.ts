import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SigoSharedModule } from 'app/shared/shared.module';
import { ForneceComponent } from './fornece.component';
import { ForneceDetailComponent } from './fornece-detail.component';
import { ForneceUpdateComponent } from './fornece-update.component';
import { ForneceDeleteDialogComponent } from './fornece-delete-dialog.component';
import { forneceRoute } from './fornece.route';

@NgModule({
  imports: [SigoSharedModule, RouterModule.forChild(forneceRoute)],
  declarations: [ForneceComponent, ForneceDetailComponent, ForneceUpdateComponent, ForneceDeleteDialogComponent],
  entryComponents: [ForneceDeleteDialogComponent],
})
export class SigoForneceModule {}
