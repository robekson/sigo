import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { ForneceDetailComponent } from 'app/entities/fornece/fornece-detail.component';
import { Fornece } from 'app/shared/model/fornece.model';

describe('Component Tests', () => {
  describe('Fornece Management Detail Component', () => {
    let comp: ForneceDetailComponent;
    let fixture: ComponentFixture<ForneceDetailComponent>;
    const route = ({ data: of({ fornece: new Fornece(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ForneceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ForneceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ForneceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fornece on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fornece).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
